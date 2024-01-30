package com.financetracker.savingsgoal.infrastructure.client.transaction;

import org.openapitools.client.ApiException;
import org.openapitools.client.api.OneTimeTransactionApi;
import org.openapitools.client.model.OneTimeTransactionDto;
import org.springframework.stereotype.Component;

@Component
public class TransactionClient implements TransactionProvider{

    private static final String BASE_URL_TRANSACTION = "http://localhost:8080";

    private final OneTimeTransactionApi oneTimeTransactionApi;

    public TransactionClient() {
        oneTimeTransactionApi = new OneTimeTransactionApi();
        oneTimeTransactionApi.setCustomBaseUrl(BASE_URL_TRANSACTION);
    }

    @Override
    public void createOneTimeTransaction(OneTimeTransactionDto oneTimeTransactionDto) {
        try {
            oneTimeTransactionApi.transactionsOnetimePost(oneTimeTransactionDto);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }
}
