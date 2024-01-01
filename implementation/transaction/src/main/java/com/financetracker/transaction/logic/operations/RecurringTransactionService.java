package com.financetracker.transaction.logic.operations;

import com.financetracker.transaction.api.exceptions.NotParseableException;
import com.financetracker.transaction.api.exceptions.TransferFailedException;
import com.financetracker.transaction.infrastructure.db.TransactionRepository;
import com.financetracker.transaction.logic.model.RecurringTransaction;
import com.financetracker.transaction.logic.model.TransactionRecord;
import com.financetracker.transaction.logic.model.TransferStatus;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecurringTransactionService {

    private final TransferService transferService;
    private final TransactionRepository<RecurringTransaction> recurringTransactionRepository;

    public List<RecurringTransaction> getRecurringTransactions() {
        return recurringTransactionRepository.findAll();
    }

    public Optional<RecurringTransaction> getRecurringTransaction(final String transactionId) {
        return recurringTransactionRepository.findById(transactionId);
    }

    public void createRecurringTransaction(final RecurringTransaction recurringTransaction) {
        if (transferService.requiredBankAccountsDoNotExist(recurringTransaction.getTransfer())) {
            throw new NotParseableException();
        }

        recurringTransactionRepository.save(recurringTransaction);
    }

    public void updateRecurringTransaction(final String transactionId, final RecurringTransaction recurringTransaction) {
        if (transferService.requiredBankAccountsDoNotExist(recurringTransaction.getTransfer())) {
            throw new NotParseableException();
        }

        final RecurringTransaction originalTransaction = getRecurringTransaction(transactionId).orElseThrow(NotFoundException::new);
        originalTransaction.getTransactionRecords().forEach(record -> transferService.rollbackTransfer(originalTransaction.getTransfer(), record));
        recurringTransactionRepository.save(recurringTransaction);
    }


    public void deleteRecurringTransaction(final String transactionId) {
        final RecurringTransaction transaction = getRecurringTransaction(transactionId).orElseThrow(NotFoundException::new);

        transaction.getTransactionRecords().forEach(record -> transferService.rollbackTransfer(transaction.getTransfer(), record));
        recurringTransactionRepository.deleteById(transactionId);
    }

    public void transferTransactionRecordAndSetStatus(final String transactionId, final String recordId) {
        final RecurringTransaction recurringTransaction = getRecurringTransaction(transactionId).orElseThrow(NotFoundException::new);

        final TransactionRecord transactionRecord = recurringTransaction.getTransactionRecords().stream()
                .filter(record -> record.getId().equals(recordId))
                .findFirst()
                .orElseThrow(NotFoundException::new);

        try {
            transferService.transfer(recurringTransaction.getTransfer(), transactionRecord);
        } catch (TransferFailedException e) {
            transactionRecord.setTransferStatus(TransferStatus.FAILED);
            recurringTransactionRepository.save(recurringTransaction);
            throw e;
        }

        transactionRecord.setTransferStatus(TransferStatus.SUCCESSFUL);
        recurringTransactionRepository.save(recurringTransaction);
    }
}
