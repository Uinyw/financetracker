package com.bank.account;

import java.util.UUID;
import org.openapitools.model.BankAccount;
import org.openapitools.model.MonetaryAmount;
import org.openapitools.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/*
@Service
public class BankAccountService {
    
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    private List<Transaction> transactions;

    public void updateBalance(BankAccount bankAccount) {
        MonetaryAmount monetaryAmount = new MonetaryAmount();
        double totalAmount = transactions.stream()
                .mapToDouble(transaction -> transaction.getAmount().getAmount())
                .sum();
        monetaryAmount.setAmount(totalAmount);
        bankAccount.setBalance(monetaryAmount);
    }

    public BankAccount saveBankAccount(BankAccount bankAccount) {
        return bankAccountRepository.saveAndFlush(bankAccount);
    }

    public BankAccount getBankAccountById(UUID id) {
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(id);
        return optionalBankAccount.orElse(null);
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccountRepository.findAll();
    }
}
*/