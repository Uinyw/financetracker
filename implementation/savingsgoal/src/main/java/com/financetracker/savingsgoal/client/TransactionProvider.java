package com.financetracker.savingsgoal.client;

import org.openapitools.client.model.BankAccountDto;
import org.openapitools.client.model.OneTimeTransactionDto;

import java.util.Optional;

public interface TransactionProvider {

    void transactionsOnetimePost(OneTimeTransactionDto oneTimeTransactionDto);
    Optional<OneTimeTransactionDto> transactionsOnetimeIdGet(String id);
}
