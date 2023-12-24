package com.financetracker.transaction.logic.operations;

import com.financetracker.transaction.api.exceptions.TransactionFailedException;
import com.financetracker.transaction.infrastructure.db.TransactionRepository;
import com.financetracker.transaction.infrastructure.client.BankAccountProvider;
import com.financetracker.transaction.logic.model.OneTimeTransaction;
import lombok.RequiredArgsConstructor;
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
        if (oneTimeTransaction.isInternalTransfer() && !sourceBankAccountBalanceIsSufficientForTransaction(oneTimeTransaction)) {
            throw new TransactionFailedException();
        }

        bankAccountProvider.getBankAccount(oneTimeTransaction.getTransfer().sourceBankAccountId());
        oneTimeTransactionRepository.save(oneTimeTransaction);
    }

    private boolean sourceBankAccountBalanceIsSufficientForTransaction(final OneTimeTransaction oneTimeTransaction) {
        final var bankAccount = bankAccountProvider.getBankAccount(oneTimeTransaction.getTransfer().sourceBankAccountId());
        return bankAccount.filter(bankAccountDto -> bankAccountDto.getBalance() != null && bankAccountDto.getBalance().getAmount() != null && bankAccountDto.getBalance().getAmount() >= oneTimeTransaction.getAmount().amount().doubleValue()).isPresent();
    }

    public void updateOneTimeTransaction(final OneTimeTransaction oneTimeTransaction) {
        oneTimeTransactionRepository.save(oneTimeTransaction);
    }


    public void deleteOneTimeTransaction(final String transactionId) {
        oneTimeTransactionRepository.deleteById(transactionId);
    }


}
