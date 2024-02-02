package com.financetracker.savingsgoal.logic.operations;

import com.financetracker.savingsgoal.infrastructure.client.bankaccount.BankAccountProvider;
import com.financetracker.savingsgoal.infrastructure.client.transaction.TransactionProvider;
import com.financetracker.savingsgoal.infrastructure.config.Configuration;
import com.financetracker.savingsgoal.infrastructure.db.PeriodicalSavingsGoalRepository;
import com.financetracker.savingsgoal.logic.model.AchievementStatus;
import com.financetracker.savingsgoal.logic.model.MonetaryAmount;
import com.financetracker.savingsgoal.logic.model.PeriodicalSavingsGoal;
import com.financetracker.savingsgoal.logic.model.SavingsRecord;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class PeriodicalSavingsGoalTransferLogic {

    private final TransactionProvider transactionProvider;
    private final BankAccountProvider bankAccountProvider;

    private final PeriodicalSavingsGoalRepository repository;

    private final Clock clock;

    /**
     * Runs every day at 6am.
     */
    @Scheduled(cron = "0 0 6 * * *", zone = Configuration.TIME_ZONE)
    public void transferRecurringTransactionsOnDueDate() {
        repository.findAll().stream()
                .filter(periodicalSavingsGoal -> isDue(periodicalSavingsGoal, LocalDate.now(clock)))
                .forEach(this::transferSavingsGoal);
    }

    /**
     * Runs every day at 6am.
     */
    @Scheduled(cron = "0 0 6 * * *", zone = Configuration.TIME_ZONE)
    public void checkIfSavingsGoalAchieved() {
        repository.findAll().stream()
                .filter(periodicalSavingsGoal -> isFinished(periodicalSavingsGoal, LocalDate.now(clock)))
                .forEach(this::setSavingGoalStatus);
    }

    private boolean isFinished(final PeriodicalSavingsGoal periodicalSavingsGoal, final LocalDate date) {
        if (periodicalSavingsGoal.getDuration().getEnd() == null) {
            return false;
        }

        return periodicalSavingsGoal.getDuration().getEnd().isBefore(date);
    }

    private void setSavingGoalStatus(final PeriodicalSavingsGoal periodicalSavingsGoal) {
        final var savedAmount = periodicalSavingsGoal.getSavingsRecords().stream()
                .filter(savingsRecord -> savingsRecord.getAchievementStatus().equals(AchievementStatus.ACHIEVED))
                .map(SavingsRecord::getAmount)
                .mapToDouble(MonetaryAmount::getAmount)
                .sum();

        final var savingGoalAchieved = periodicalSavingsGoal.getGoal().getAmount() <= savedAmount;

        periodicalSavingsGoal.setAchievementStatus(savingGoalAchieved ? AchievementStatus.ACHIEVED : AchievementStatus.FAILED);
        repository.save(periodicalSavingsGoal);
    }


    private boolean isDue(final PeriodicalSavingsGoal periodicalSavingsGoal, final LocalDate date) {
        if (periodicalSavingsGoal.getDuration().getStart().isAfter(date) || periodicalSavingsGoal.getDuration().getEnd().isBefore(date)) {
            return false;
        }

        return periodicalSavingsGoal.getDuration().getStart().equals(date) || isDue(periodicalSavingsGoal, date.minusMonths(periodicalSavingsGoal.getPeriodicity().getMonths()));
    }

    private void transferSavingsGoal(final PeriodicalSavingsGoal periodicalSavingsGoal) {
        final Double amountToTransfer = getAmountToTransfer(periodicalSavingsGoal);
        final boolean transferResult = transferAmount(periodicalSavingsGoal, amountToTransfer);

        final var savingsRecord = SavingsRecord.with()
                .id(UUID.randomUUID())
                .savingsGoalId(periodicalSavingsGoal.getId())
                .achievementStatus(transferResult ? AchievementStatus.ACHIEVED : AchievementStatus.FAILED)
                .amount(new MonetaryAmount(amountToTransfer))
                .build();

        periodicalSavingsGoal.getSavingsRecords().add(savingsRecord);

        if (!transferResult) {
            periodicalSavingsGoal.setAchievementStatus(AchievementStatus.FAILED);
        }

        repository.save(periodicalSavingsGoal);
    }

    private Double getAmountToTransfer(final PeriodicalSavingsGoal periodicalSavingsGoal) {
        if (periodicalSavingsGoal.getRecurringAmount() != null) {
            return periodicalSavingsGoal.getRecurringAmount().getAmount();
        }

        final Optional<BankAccountDto> bankAccount = bankAccountProvider.getBankAccount(periodicalSavingsGoal.getSourceBankAccountId().toString());
        return bankAccount.map(bankAccountDto -> bankAccountDto.getBalance().getAmount() * periodicalSavingsGoal.getRecurringRate()).orElse(0.0);
    }

    private boolean transferAmount(final PeriodicalSavingsGoal periodicalSavingsGoal, final Double amount) {
        return transactionProvider.createAndTransferOneTimeTransaction(OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Savings Goal " + periodicalSavingsGoal.getName())
                .type(TypeDto.SHIFT)
                .amount(MonetaryAmountDto.builder().amount(amount).build())
                .transfer(TransferDto.builder().sourceBankAccountId(periodicalSavingsGoal.getSourceBankAccountId()).targetBankAccountId(periodicalSavingsGoal.getTargetBankAccountId()).build())
                .date(LocalDate.now(clock).toString())
                .build()
        );
    }
}
