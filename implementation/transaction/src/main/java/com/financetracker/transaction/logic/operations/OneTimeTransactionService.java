package com.financetracker.transaction.logic.operations;

import com.financetracker.transaction.infrastructure.TransactionRepository;
import com.financetracker.transaction.infrastructure.client.BankAccountProvider;
import com.financetracker.transaction.logic.model.OneTimeTransaction;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.api.BankAccountApi;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OneTimeTransactionService {

    private final BankAccountProvider bankAccountProvider;
    private final TransactionRepository<OneTimeTransaction> oneTimeTransactionRepository;

    public List<OneTimeTransaction> getOneTimeTransactions() {
        return oneTimeTransactionRepository.findAll();
    }

    public Optional<OneTimeTransaction> getOneTimeTransaction(final String transactionId) {
        return oneTimeTransactionRepository.findById(transactionId);
    }

    public void createOneTimeTransaction(final OneTimeTransaction oneTimeTransaction) {
        bankAccountProvider.getBankAccount(oneTimeTransaction.getTransfer().sourceBankAccountId());
        oneTimeTransactionRepository.save(oneTimeTransaction);
    }

    public void updateOneTimeTransaction(final OneTimeTransaction oneTimeTransaction) {
        oneTimeTransactionRepository.save(oneTimeTransaction);
    }


    public void deleteOneTimeTransaction(final String transactionId) {
        oneTimeTransactionRepository.deleteById(transactionId);
    }


}
