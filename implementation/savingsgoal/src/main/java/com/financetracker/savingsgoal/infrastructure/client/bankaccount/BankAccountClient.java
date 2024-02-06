package com.financetracker.savingsgoal.infrastructure.client.bankaccount;

import org.openapitools.client.ApiException;
import org.openapitools.client.api.BankAccountApi;
import org.openapitools.client.model.BankAccountDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BankAccountClient implements BankAccountProvider {

    @Value("${bank-account}")
    private String host;

    private final BankAccountApi bankAccountApi;

    public BankAccountClient() {
        bankAccountApi = new BankAccountApi();
    }

    @Override
    public Optional<BankAccountDto> getBankAccount(String id) {
        setBaseUrl();
        try {
            return Optional.of(bankAccountApi.bankAccountsIdGet(id));
        } catch (ApiException e) {
            return Optional.empty();
        }
    }

    private void setBaseUrl() {
        if (bankAccountApi.getCustomBaseUrl() != null) {
            return;
        }

        bankAccountApi.setCustomBaseUrl("http://" + host + ":8081");
    }
}
