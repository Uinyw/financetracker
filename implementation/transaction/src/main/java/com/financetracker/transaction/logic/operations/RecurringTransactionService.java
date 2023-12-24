package com.financetracker.transaction.logic.operations;

import com.financetracker.transaction.infrastructure.TransactionRepository;
import com.financetracker.transaction.logic.model.OneTimeTransaction;
import com.financetracker.transaction.logic.model.RecurringTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecurringTransactionService {

    private final TransactionRepository<RecurringTransaction> recurringTransactionRepository;

    public List<RecurringTransaction> getRecurringTransactions() {
        return recurringTransactionRepository.findAll();
    }

    public Optional<RecurringTransaction> getRecurringTransaction(final String transactionId) {
        return recurringTransactionRepository.findById(transactionId);
    }

    public void createRecurringTransaction(final RecurringTransaction recurringTransaction) {
        recurringTransactionRepository.save(recurringTransaction);
    }

    public void updateRecurringTransaction(final RecurringTransaction recurringTransaction) {
        recurringTransactionRepository.save(recurringTransaction);
    }


    public void deleteRecurringTransaction(final String transactionId) {
        recurringTransactionRepository.deleteById(transactionId);
    }
}
