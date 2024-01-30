package com.financetracker.savingsgoal.logic.operations;

import com.financetracker.savingsgoal.infrastructure.client.bankaccount.BankAccountProvider;
import com.financetracker.savingsgoal.infrastructure.client.transaction.TransactionProvider;
import com.financetracker.savingsgoal.logic.model.PeriodicalSavingsGoal;
import com.financetracker.savingsgoal.infrastructure.config.Configuration;
import com.financetracker.savingsgoal.api.mapping.PeriodicalSavingsGoalMapper;
import com.financetracker.savingsgoal.infrastructure.db.PeriodicalSavingsGoalRepository;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.*;
import org.openapitools.model.PeriodicalSavingsGoalDTO;
import org.openapitools.model.Periodicity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class PeriodicalSavingsGoalLogic {

    private final TransactionProvider transactionProvider;
    private final BankAccountProvider bankAccountProvider;
    private final PeriodicalSavingsGoalRepository periodicalSavingsGoalRepository;
    private final PeriodicalSavingsGoalMapper savingsGoalMapper;
    private final Clock clock;

    public List<PeriodicalSavingsGoalDTO> getPeriodicalSavingsGoals() {
        List<PeriodicalSavingsGoalDTO> periodicalSavingsGoalDTOList = new ArrayList<>();
        for (PeriodicalSavingsGoal sg : periodicalSavingsGoalRepository.findAll()) {
            periodicalSavingsGoalDTOList.add(savingsGoalMapper.periodicalSavingsGoalEntityToDTO(sg));
        }
        return periodicalSavingsGoalDTOList;
    }

    public void createPeriodicalSavingsGoal(PeriodicalSavingsGoalDTO periodicalSavingsGoalDTO) {
        PeriodicalSavingsGoal periodicalSavingsGoal = savingsGoalMapper.periodicalSavingsGoalDTOtoEntity(periodicalSavingsGoalDTO);
        periodicalSavingsGoalRepository.save(periodicalSavingsGoal);
    }

    public boolean deletePeriodicalSavingsGoal(String id) {
        PeriodicalSavingsGoal psg = findPeriodicalSavingsGoalById(id);
        if (psg == null)
            return false;
        periodicalSavingsGoalRepository.delete(psg);
        return true;
    }

    public PeriodicalSavingsGoalDTO getPeriodicalSavingsGoal(String id) {
        PeriodicalSavingsGoal psg = findPeriodicalSavingsGoalById(id);
        return savingsGoalMapper.periodicalSavingsGoalEntityToDTO(psg);
    }

    private PeriodicalSavingsGoal findPeriodicalSavingsGoalById(String id) {
        for (PeriodicalSavingsGoal sg : periodicalSavingsGoalRepository.findAll()) {
            if (sg.getId().toString().equals(id)) {
                return sg;
            }
        }
        return null;
    }

    /**
     * Runs every day at 6am.
     */
    @Scheduled(cron = "0 0 6 * * *", zone = Configuration.TIME_ZONE)
    public void transferRecurringTransactionsOnDueDate() {
        List<PeriodicalSavingsGoal> periodicalSavingsGoals = periodicalSavingsGoalRepository.findAll();
        for (PeriodicalSavingsGoal psg : periodicalSavingsGoals) {
            if (checkDue(psg, LocalDate.now(clock))) {
                try {
                    double amount = psg.getRecurringAmount().getAmount();

                    if (psg.getRecurringRate() != null) {
                        Optional<BankAccountDto> bankAccount = bankAccountProvider.getBankAccount(psg.getSourceBankAccountId().toString());

                        if (bankAccount.isEmpty()) {
                            return;
                        }

                        amount = bankAccount.get().getBalance().getAmount() * psg.getRecurringRate();
                    }

                    transactionProvider.createOneTimeTransaction(OneTimeTransactionDto.builder()
                            .id(UUID.randomUUID())
                            .name("Savings Goal " + psg.getName())
                            .type(TypeDto.SHIFT)
                            .amount(MonetaryAmountDto.builder().amount(amount).build())
                            .transfer(TransferDto.builder().sourceBankAccountId(psg.getSourceBankAccountId()).targetBankAccountId(psg.getTargetBankAccountId()).build())
                            .date(LocalDate.now(clock).toString())
                            .build()
                    );
                } catch (Exception e) {
                    System.out.println(e + "\n Couldn't send the transaction");
                }
            }
        }
    }

    private boolean checkDue(PeriodicalSavingsGoal periodicalSavingsGoal, LocalDate date) {
        if (periodicalSavingsGoal.getDuration().getStart().isAfter(date) || periodicalSavingsGoal.getDuration().getEnd().isBefore(date)) {
            return false;
        }

        return periodicalSavingsGoal.getDuration().getStart().equals(date) || checkDue(periodicalSavingsGoal, date.minusMonths(periodicityToInt(periodicalSavingsGoal.getPeriodicity())));
    }

    private int periodicityToInt(Periodicity periodicity) {
        int value = 1;
        switch (periodicity) {
            case MONTHLY -> value = 1;
            case YEARLY -> value = 12;
            case QUARTERLY -> value = 3;
            case HALF_YEARLY -> value = 6;
        }
        return value;
    }
}
