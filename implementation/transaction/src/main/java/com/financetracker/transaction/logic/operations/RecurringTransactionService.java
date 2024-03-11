package com.financetracker.transaction.logic.operations;

import com.financetracker.transaction.api.exceptions.NotParseableException;
import com.financetracker.transaction.api.exceptions.TransferFailedException;
import com.financetracker.transaction.infrastructure.db.RecurringTransactionRepository;
import com.financetracker.transaction.infrastructure.kafka.MessagePublisher;
import com.financetracker.transaction.infrastructure.kafka.model.UpdateType;
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
    private final RecurringTransactionRepository recurringTransactionRepository;
    private final MessagePublisher messagePublisher;

    public List<RecurringTransaction> getRecurringTransactions() {
        return recurringTransactionRepository.findAll();
    }

    public Optional<RecurringTransaction> getRecurringTransaction(final String transactionId) {
        return recurringTransactionRepository.findById(transactionId);
    }

    public void createRecurringTransaction(final RecurringTransaction recurringTransaction) {
        if (transferService.requiredBankAccountsDoesNotExist(recurringTransaction.getTransfer())) {
            throw new NotParseableException();
        }

        recurringTransactionRepository.save(recurringTransaction);
        messagePublisher.publishMessageRecurringTransactionUpdate(recurringTransaction, UpdateType.CREATE);
    }

    public void createTransactionRecordForRecurringTransaction(final TransactionRecord transactionRecord) {
        final RecurringTransaction transaction = getRecurringTransaction(transactionRecord.getTransactionId()).orElseThrow(NotFoundException::new);
        transaction.getTransactionRecords().add(transactionRecord);
        recurringTransactionRepository.save(transaction);
        messagePublisher.publishMessageRecurringTransactionUpdate(transaction, UpdateType.UPDATE);
    }

    public void deleteTransactionRecord(final String transactionId, final String transactionRecordId) {
        final RecurringTransaction transaction = getRecurringTransaction(transactionId).orElseThrow(NotFoundException::new);

        final TransactionRecord recordToDelete = transaction.getTransactionRecords().stream().filter(record -> record.getId().equals(transactionRecordId)).findFirst().orElseThrow(NotFoundException::new);
        transferService.rollbackTransfer(transaction.getTransfer(), recordToDelete);

        transaction.getTransactionRecords().removeIf(record -> record.getId().equals(transactionRecordId));
        recurringTransactionRepository.save(transaction);
        messagePublisher.publishMessageRecurringTransactionUpdate(transaction, UpdateType.UPDATE);
    }

    public void updateRecurringTransaction(final String transactionId, final RecurringTransaction recurringTransaction) {
        if (transferService.requiredBankAccountsDoesNotExist(recurringTransaction.getTransfer())) {
            throw new NotParseableException();
        }

        final RecurringTransaction originalTransaction = getRecurringTransaction(transactionId).orElseThrow(NotFoundException::new);
        recurringTransaction.setTransactionRecords(originalTransaction.getTransactionRecords());
        recurringTransactionRepository.save(recurringTransaction);
        messagePublisher.publishMessageRecurringTransactionUpdate(recurringTransaction, UpdateType.UPDATE);
    }


    public void deleteRecurringTransaction(final String transactionId) {
        final RecurringTransaction transaction = getRecurringTransaction(transactionId).orElseThrow(NotFoundException::new);

        transaction.getTransactionRecords().forEach(record -> transferService.rollbackTransfer(transaction.getTransfer(), record));
        recurringTransactionRepository.deleteById(transactionId);
        messagePublisher.publishMessageRecurringTransactionUpdate(transaction, UpdateType.DELETE);
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
