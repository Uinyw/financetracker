package com.financetracker.savingsgoal.infrastructure.client.transaction;

import org.openapitools.client.model.OneTimeTransactionDto;

public interface TransactionProvider {
    void createOneTimeTransaction(OneTimeTransactionDto oneTimeTransactionDto);
}
