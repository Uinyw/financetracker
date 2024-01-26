package com.financetracker.transaction.infrastructure.client;

import org.openapitools.client.ApiException;
import org.openapitools.client.api.BankAccountApi;
import org.openapitools.client.model.BankAccountDto;
import org.openapitools.client.model.MonetaryAmountDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BankAccountClient implements BankAccountProvider {

    private static final String BASE_URL_BANK_ACCOUNT = "http://localhost:8081";

    private final BankAccountApi bankAccountApi;

    public BankAccountClient() {
        bankAccountApi = new BankAccountApi();
        bankAccountApi.setCustomBaseUrl(BASE_URL_BANK_ACCOUNT);
    }

    @Override
    public Optional<BankAccountDto> getBankAccount(String id) {
        try {
            return Optional.of(bankAccountApi.bankAccountsIdGet(id));
        } catch (ApiException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean updateBankAccountBalance(BankAccountDto bankAccountDto, Double deltaAmount) {
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

}
