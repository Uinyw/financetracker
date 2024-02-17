package com.financetracker.transaction.logic.operations;

import com.financetracker.transaction.infrastructure.client.BankAccountProvider;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.BankAccountDto;
import org.openapitools.client.model.MonetaryAmountDto;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class MockBankAccountProvider implements BankAccountProvider {

    private final List<BankAccountDto> bankAccounts;

    @Override
    public Optional<BankAccountDto> getBankAccount(String id) {
        return bankAccounts.stream().filter(account -> account.getId().toString().equals(id)).findFirst();
    }

    @Override
    public boolean updateBankAccountBalance(BankAccountDto bankAccountDto, Double deltaAmount) {
        final var newAmount = MonetaryAmountDto.builder().amount(bankAccountDto.getBalance().getAmount() + deltaAmount).build();
        bankAccountDto.setBalance(newAmount);
        return true;
    }
}
