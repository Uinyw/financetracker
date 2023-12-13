package com.bank.account;

import java.util.UUID;

import com.bank.account.model.BankAccountEntity;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.BankAccount;
import org.openapitools.model.MonetaryAmount;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class BankAccountService {

    private final BankAccountFactory bankAccountFactory;
    private final BankAccountRepository bankAccountRepository;

    public List<BankAccountEntity> get() {
        return bankAccountRepository.findAll();
    }

    public BankAccount getBankAccountById(String id) {
        Optional<BankAccountEntity> bankAccountEntity = get().stream().filter(ba -> ba.getId().toString().equals(id))
                .findFirst();
        return BankAccountMapper.EntityToBankAccount(bankAccountEntity.get());
    }

    public List<BankAccount> getBankAccounts() {
         List<BankAccount> bankAccountEntities = get().stream()
                .map(ba -> BankAccountMapper.EntityToBankAccount(ba)).toList();
        return bankAccountEntities;
    }

    public Boolean deleteBankAccountById(String id) {
        Optional<BankAccountEntity> bankAccountEntity = get().stream().filter(ba -> ba.getId().toString().equals(id))
                .findFirst();
        if (bankAccountEntity.isPresent()) {
            bankAccountRepository.delete(bankAccountEntity.get());
            return true;
        }
        return false;
    }

    public void updateBalance(BankAccount bankAccount) {
        MonetaryAmount monetaryAmount = new MonetaryAmount();
        double totalAmount = bankAccount.getTransactions().stream()
                .mapToDouble(transaction -> transaction.getAmount().getAmount())
                .sum();
        monetaryAmount.setAmount(totalAmount);
        bankAccount.setBalance(monetaryAmount);
    }

    public BankAccount saveBankAccount(BankAccount bankAccount) {
        final var x = BankAccountEntity.with()
                .id(UUID.randomUUID())
                .name("Test")
                .build();
        bankAccountRepository.save(x);

        return null;
    }

    public BankAccount createAndSaveRandomAccount() {
        BankAccount bankAccount = bankAccountFactory.createRandomAccount();
        updateBalance(bankAccount);
        bankAccountRepository.save(BankAccountEntity.with()
                .id(UUID.randomUUID())
                .name("Test")
                .balance(Math.random())
                .build());
        return bankAccount;
    }

}
