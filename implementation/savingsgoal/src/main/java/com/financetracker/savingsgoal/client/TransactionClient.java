package com.financetracker.savingsgoal.client;

import org.openapitools.client.ApiException;
import org.openapitools.client.api.BankAccountApi;
import org.openapitools.client.api.OneTimeTransactionApi;
import org.openapitools.client.model.OneTimeTransactionDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TransactionClient implements TransactionProvider{

    private static final String BASE_URL_TRANNSACTION = "http://localhost:8080";

    private final OneTimeTransactionApi oneTimeTransactionApi;

    public TransactionClient() {
        oneTimeTransactionApi = new OneTimeTransactionApi();
        oneTimeTransactionApi.setCustomBaseUrl(BASE_URL_TRANNSACTION);
    }

    @Override
    public void transactionsOnetimePost(OneTimeTransactionDto oneTimeTransactionDto) {
        try {
            oneTimeTransactionApi.transactionsOnetimePost(oneTimeTransactionDto);
        } catch (ApiException e) {
            System.out.println("TransactionPost didn't succeed");
        }
    }

    @Override
    public Optional<OneTimeTransactionDto> transactionsOnetimeIdGet(String id) {
        try {
            return Optional.of(oneTimeTransactionApi.transactionsOnetimeIdGet(id));
        } catch (ApiException e) {
            return Optional.empty();
        }
    }
}
