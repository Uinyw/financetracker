package com.financetracker.transaction.data;

import org.openapitools.client.model.BankAccountDto;
import org.openapitools.client.model.MonetaryAmountDto;

public class TestBankAccountFactory {

    public static BankAccountDto createDto(final Double balance) {
        final var bankAccount = new BankAccountDto();
        final var monetaryAmount = new MonetaryAmountDto();
        monetaryAmount.setAmount(balance);
        bankAccount.setBalance(monetaryAmount);
        return bankAccount;
    }
}
