package com.bank.account.data;

import org.openapitools.model.BankAccountDto;
import org.openapitools.model.MonetaryAmountDto;

import java.util.UUID;

public class TestBankAccountBuilder {

    public static BankAccountDto buildWithDefaults() {
        return BankAccountDto.builder()
                .id(UUID.randomUUID())
                .balance(MonetaryAmountDto.builder().amount(1000.0).build())
                .build();
    }
}
