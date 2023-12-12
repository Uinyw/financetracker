package com.bank.account;

import java.util.ArrayList;
import java.util.UUID;

import org.openapitools.model.BankAccount;
import org.openapitools.model.MonetaryAmount;
import org.openapitools.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountFactory {

    
    /*
    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountFactory(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }*/

    public BankAccount createBankAccount(String name, String description) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(UUID.randomUUID());
        bankAccount.setName(name);
        bankAccount.setDescription(description);
        List<Transaction> transactions = new ArrayList<Transaction>();
        bankAccount.transactions(transactions);
        MonetaryAmount balance = new MonetaryAmount();
        balance.setAmount(0.0);
        bankAccount.setBalance(balance);
        List<String> labels = new ArrayList<String>();
        bankAccount.setLabels(labels);

        //bankAccountService.saveBankAccount(bankAccount);

        return bankAccount;
    }

    public BankAccount createBankAccount(String name, String description, List<String> labels) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(UUID.randomUUID());
        bankAccount.setName(name);
        bankAccount.setDescription(description);
        List<Transaction> transactions = new ArrayList<Transaction>();
        bankAccount.transactions(transactions);
        MonetaryAmount balance = new MonetaryAmount();
        balance.setAmount(0.0);
        bankAccount.setBalance(balance);
        bankAccount.setLabels(labels);

        //bankAccountService.saveBankAccount(bankAccount);

        return bankAccount;
    }
}