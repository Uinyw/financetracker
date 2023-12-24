package com.financetracker.transaction.infrastructure.client;

import org.openapitools.client.model.BankAccountDto;

import java.util.Optional;

public interface BankAccountProvider {
    Optional<BankAccountDto> getBankAccount(String id);
}
