package com.financetracker.transaction.logic.operations;

import com.financetracker.transaction.infrastructure.config.Configuration;
import com.financetracker.transaction.logic.model.RecurringTransaction;
import com.financetracker.transaction.logic.model.TransactionRecord;
import com.financetracker.transaction.logic.model.TransferStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class TransferScheduler {

    private final Clock clock;
    private final OneTimeTransactionService oneTimeTransactionService;
    private final RecurringTransactionService recurringTransactionService;

    /**
     * Runs every day at 6am.
     */
    @Scheduled(cron = "0 0 6 * * *", zone = Configuration.TIME_ZONE)
    public void transferOneTimeTransactionsOnDueDate() {
        oneTimeTransactionService.getOneTimeTransactions().forEach(transaction -> {
            if (transaction.getTransferStatus().equals(TransferStatus.INITIAL) && transactionIsDue(transaction.getDate())) {
                oneTimeTransactionService.transferOneTimeTransactionAndSetStatus(transaction.getId());
            }
        });
    }

    /**
     * Runs every day at 6am.
     */
    @Scheduled(cron = "0 0 6 * * *", zone = Configuration.TIME_ZONE)
    public void transferRecurringTransactionsOnDueDate() {
        recurringTransactionService.getRecurringTransactions().forEach(transaction -> {
            if (!transaction.isEnabledForAutomaticScheduling()) {
                return;
            }

            transferRecurringTransactionOnDate(transaction, transaction.getStartDate());
        });
    }

    private void transferRecurringTransactionOnDate(final RecurringTransaction transaction, final LocalDate date) {
        if (!transactionIsDue(date)) {
            return;
        }

        if (transaction.getTransactionRecords().stream().anyMatch(record -> record.getDate().equals(date))) {
            transferRecurringTransactionOnDate(transaction, date.plusMonths(transaction.getPeriodicity().getMonths()));
            return;
        }

        final TransactionRecord transactionRecord = TransactionRecord.with()
                .id(UUID.randomUUID().toString())
                .transactionId(transaction.getId())
                .amount(transaction.getFixedAmount())
                .date(date)
                .transferStatus(TransferStatus.INITIAL)
                .build();

        recurringTransactionService.createTransactionRecordForRecurringTransaction(transactionRecord);
        recurringTransactionService.transferTransactionRecordAndSetStatus(transaction.getId(), transactionRecord.getId());

        transferRecurringTransactionOnDate(transaction, date.plusMonths(transaction.getPeriodicity().getMonths()));
    }

    private boolean transactionIsDue(final LocalDate date) {
        return date.equals(LocalDate.now(clock)) || date.isBefore(LocalDate.now(clock));
    }
}
