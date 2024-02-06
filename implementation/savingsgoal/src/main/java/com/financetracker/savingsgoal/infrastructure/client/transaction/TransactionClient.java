package com.financetracker.savingsgoal.infrastructure.client.transaction;

import org.openapitools.client.ApiException;
import org.openapitools.client.api.OneTimeTransactionApi;
import org.openapitools.client.model.OneTimeTransactionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TransactionClient implements TransactionProvider{

    @Value("${transaction}")
    private String host;

    private final OneTimeTransactionApi oneTimeTransactionApi;

    public TransactionClient() {
        oneTimeTransactionApi = new OneTimeTransactionApi();
    }

    @Override
    public boolean createAndTransferOneTimeTransaction(OneTimeTransactionDto oneTimeTransactionDto) {
        setBaseUrl();
        try {
            oneTimeTransactionApi.transactionsOnetimePost(oneTimeTransactionDto);
            oneTimeTransactionApi.transactionsOnetimeIdTransferPost(oneTimeTransactionDto.getId().toString());
            return true;
        } catch (ApiException e) {
            return false;
        }
    }

    private void setBaseUrl() {
        if (oneTimeTransactionApi.getCustomBaseUrl() != null) {
            return;
        }

        oneTimeTransactionApi.setCustomBaseUrl("http://" + host + ":8080");
    }
}
