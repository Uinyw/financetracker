package com.bank.account.data;

import org.openapitools.model.BankAccountDto;
import org.openapitools.model.MonetaryAmountDto;

import java.util.UUID;

public class TestBankAccountFactory {

    public static BankAccountDto createDto() {
        final var bankAccountDto = new BankAccountDto();
        bankAccountDto.setId(UUID.randomUUID());
        bankAccountDto.setBalance(createMonetaryAmountDto(1000.0));
        return bankAccountDto;
    }


    private static MonetaryAmountDto createMonetaryAmountDto(final Double amount) {
        final var result = new MonetaryAmountDto();
        result.setAmount(amount);
        return result;
    }
}
