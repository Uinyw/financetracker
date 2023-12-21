package com.bank.account;

import java.util.ArrayList;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.openapitools.model.BankAccount;
import org.openapitools.model.MonetaryAmount;
import org.openapitools.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class BankAccountFactory {

    /**
     * Creates a new BankAccount with a random UUID, a name, a description, an empty list of transactions, a balance of 0.0 and an empty list of labels.
     * 
     * @param name 
     * @param description 
     * @return BankAccount
     */
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

        return bankAccount;
    }

    /**
     * Creates a new BankAccount with a random UUID, a name, a description, an empty list of transactions, a balance of 0.0 and a list of labels.
     * 
     * @param name
     * @param description
     * @param labels
     * @return BankAccount
     */
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

        return bankAccount;
    }

    /**
     * Creates a new BankAccount with a random UUID, a name, a description, a list of transactions, a balance of 0.0 and an empty list of labels.
     * 
     * @return BankAccount
     */
    public BankAccount createRandomAccount(){
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(UUID.randomUUID());

        Transaction transaction = new Transaction();
        MonetaryAmount money = new MonetaryAmount();
        money.setAmount(Math.random());
        transaction.amount(money);
        
        bankAccount.addTransactionsItem(transaction);

        return bankAccount;
    }
}