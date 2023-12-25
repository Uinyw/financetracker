package com.financetracker.transaction.logic.operations;

import com.financetracker.transaction.api.exceptions.NotParseableException;
import com.financetracker.transaction.api.exceptions.TransferFailedException;
import com.financetracker.transaction.infrastructure.db.TransactionRepository;
import com.financetracker.transaction.infrastructure.client.BankAccountProvider;
import com.financetracker.transaction.logic.model.OneTimeTransaction;
import com.financetracker.transaction.logic.model.Transfer;
import com.financetracker.transaction.logic.model.TransferStatus;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OneTimeTransactionService {

    private final BankAccountProvider bankAccountProvider;
    private final TransferService transferService;
    private final TransactionRepository<OneTimeTransaction> oneTimeTransactionRepository;

    public List<OneTimeTransaction> getOneTimeTransactions() {
        return oneTimeTransactionRepository.findAll();
    }

    public Optional<OneTimeTransaction> getOneTimeTransaction(final String transactionId) {
        return oneTimeTransactionRepository.findById(transactionId);
    }

    public void createOneTimeTransaction(final OneTimeTransaction oneTimeTransaction) {
        if (sourceAndTargetBankAccountDoNotExist(oneTimeTransaction.getTransfer())) {
            throw new NotParseableException();
        }

        oneTimeTransactionRepository.save(oneTimeTransaction);
    }

    public void updateOneTimeTransaction(final OneTimeTransaction oneTimeTransaction) {
        if (sourceAndTargetBankAccountDoNotExist(oneTimeTransaction.getTransfer())) {
            throw new NotParseableException();
        }

        final Optional<OneTimeTransaction> originalOneTimeTransaction = getOneTimeTransaction(oneTimeTransaction.getId());
        if (originalOneTimeTransaction.isEmpty()) {
            throw new NotFoundException();
        }

        transferService.rollbackTransfer(originalOneTimeTransaction.get().getTransfer(), originalOneTimeTransaction.get());
        oneTimeTransactionRepository.save(oneTimeTransaction);
    }

    private boolean sourceAndTargetBankAccountDoNotExist(final Transfer transfer) {
        return (transfer.isInternalTransfer() && bankAccountProvider.getBankAccount(transfer.sourceBankAccountId()).isEmpty())
                || bankAccountProvider.getBankAccount(transfer.targetBankAccountId()).isEmpty();
    }

    public void deleteOneTimeTransaction(final String transactionId) {
        final Optional<OneTimeTransaction> transaction = getOneTimeTransaction(transactionId);

        if (transaction.isEmpty()) {
            throw new NotFoundException();
        }

        transferService.rollbackTransfer(transaction.get().getTransfer(), transaction.get());
        oneTimeTransactionRepository.deleteById(transactionId);
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
        }

        oneTimeTransaction.get().setTransferStatus(TransferStatus.SUCCESSFUL);
        oneTimeTransactionRepository.save(oneTimeTransaction.get());
    }

}
