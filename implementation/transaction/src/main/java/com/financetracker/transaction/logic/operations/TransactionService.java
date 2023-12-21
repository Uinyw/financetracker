package com.financetracker.transaction.logic.operations;

import com.financetracker.transaction.infrastructure.TransactionRepository;
import com.financetracker.transaction.logic.model.OneTimeTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository<OneTimeTransaction> oneTimeTransactionRepository;

    public List<OneTimeTransaction> getOneTimeTransactions() {
        return oneTimeTransactionRepository.findAll();
    }

    public void createOneTimeTransactions(final OneTimeTransaction oneTimeTransaction) {
        oneTimeTransactionRepository.save(oneTimeTransaction);
    }


}
