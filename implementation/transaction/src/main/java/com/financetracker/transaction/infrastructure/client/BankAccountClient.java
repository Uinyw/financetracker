package com.financetracker.transaction.infrastructure.client;

import org.openapitools.client.ApiException;
import org.openapitools.client.api.BankAccountApi;
import org.openapitools.client.model.BankAccountDto;
import org.openapitools.client.model.MonetaryAmountDto;
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

    @Override
    public boolean updateBankAccountBalance(BankAccountDto bankAccountDto, Double deltaAmount) {
        setBaseUrl();
        final MonetaryAmountDto balance = new MonetaryAmountDto();
        balance.setAmount(bankAccountDto.getBalance().getAmount() + deltaAmount);
        bankAccountDto.setBalance(balance);

        try {
            bankAccountApi.bankAccountsIdPatch(bankAccountDto.getId().toString(), bankAccountDto);
            return true;
        } catch (ApiException e) {
            return false;
        }
    }

    private void setBaseUrl() {
        if (bankAccountApi.getCustomBaseUrl() != null) {
            return;
        }

        bankAccountApi.setCustomBaseUrl("http://" + host + ":8081");
    }

}
