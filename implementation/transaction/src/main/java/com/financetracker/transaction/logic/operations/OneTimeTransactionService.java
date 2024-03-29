package com.financetracker.transaction.logic.operations;

import com.financetracker.transaction.api.exceptions.NotParseableException;
import com.financetracker.transaction.api.exceptions.TransferFailedException;
import com.financetracker.transaction.infrastructure.db.OneTimeTransactionRepository;
import com.financetracker.transaction.infrastructure.kafka.MessagePublisher;
import com.financetracker.transaction.infrastructure.kafka.model.UpdateType;
import com.financetracker.transaction.logic.model.OneTimeTransaction;
import com.financetracker.transaction.logic.model.TransferStatus;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OneTimeTransactionService {

    private final TransferService transferService;
    private final OneTimeTransactionRepository oneTimeTransactionRepository;
    private final MessagePublisher messagePublisher;

    public List<OneTimeTransaction> getOneTimeTransactions() {
        return oneTimeTransactionRepository.findAll();
    }

    public Optional<OneTimeTransaction> getOneTimeTransaction(final String transactionId) {
        return oneTimeTransactionRepository.findById(transactionId);
    }

    public void createOneTimeTransaction(final OneTimeTransaction oneTimeTransaction) {
        if (transferService.requiredBankAccountsDoesNotExist(oneTimeTransaction.getTransfer())) {
            throw new NotParseableException();
        }

        oneTimeTransactionRepository.save(oneTimeTransaction);
        messagePublisher.publishMessageOneTimeTransactionUpdate(oneTimeTransaction, UpdateType.CREATE);
    }

    public void updateOneTimeTransaction(final String transactionId, final OneTimeTransaction oneTimeTransaction) {
        if (transferService.requiredBankAccountsDoesNotExist(oneTimeTransaction.getTransfer())) {
            throw new NotParseableException();
        }

        final Optional<OneTimeTransaction> originalOneTimeTransaction = getOneTimeTransaction(transactionId);
        if (originalOneTimeTransaction.isEmpty()) {
            throw new NotFoundException();
        }

        transferService.rollbackTransfer(originalOneTimeTransaction.get().getTransfer(), originalOneTimeTransaction.get());
        oneTimeTransactionRepository.save(oneTimeTransaction);
        messagePublisher.publishMessageOneTimeTransactionUpdate(oneTimeTransaction, UpdateType.UPDATE);
    }

    public void deleteOneTimeTransaction(final String transactionId) {
        final Optional<OneTimeTransaction> transaction = getOneTimeTransaction(transactionId);

        if (transaction.isEmpty()) {
            throw new NotFoundException();
        }

        transferService.rollbackTransfer(transaction.get().getTransfer(), transaction.get());
        oneTimeTransactionRepository.deleteById(transactionId);
        messagePublisher.publishMessageOneTimeTransactionUpdate(transaction.get(), UpdateType.DELETE);
    }

    public void transferOneTimeTransactionAndSetStatus(final String transactionId) {
        final Optional<OneTimeTransaction> oneTimeTransaction = getOneTimeTransaction(transactionId);
        if (oneTimeTransaction.isEmpty()) {
            throw new NotFoundException();
        }

        try {
            transferService.transfer(oneTimeTransaction.get().getTransfer(), oneTimeTransaction.get());
        } catch (TransferFailedException e) {
            oneTimeTransaction.get().setTransferStatus(TransferStatus.FAILED);
            oneTimeTransactionRepository.save(oneTimeTransaction.get());
            throw e;
        }

        oneTimeTransaction.get().setTransferStatus(TransferStatus.SUCCESSFUL);
        oneTimeTransactionRepository.save(oneTimeTransaction.get());
    }

}
