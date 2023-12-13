package com.bank.account;

import com.bank.account.model.BankAccountEntity;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.BankAccountsApi;
import org.openapitools.model.BankAccount;
import org.openapitools.model.MonetaryAmount;
import org.openapitools.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class BankAccountResource implements BankAccountsApi {

    private final BankAccountService bankAccountService;

    private List<BankAccount> tempStorage = new ArrayList<BankAccount>();

    @GetMapping("/test")
    public ResponseEntity<Void> x() {
        bankAccountService.saveBankAccount(null);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test2")
    public ResponseEntity<List<BankAccountEntity>> y() {
        return new ResponseEntity<>(bankAccountService.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<BankAccount>> bankAccountsGet() {
        
        tempStorage.add(getRandomAccount());

        System.out.println("hello");
        return new ResponseEntity<>(tempStorage, HttpStatus.OK);
    }
    
    @Override
    public ResponseEntity<Void> bankAccountsPost(@Valid @RequestBody BankAccount bankAccount) {
        bankAccount.setId(UUID.randomUUID());

        tempStorage.add(bankAccount);

        System.out.println("Hello from bankAccountsPost!");

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> bankAccountsIdDelete(@PathVariable("id") String id) {
        tempStorage.removeIf(bankAccount -> bankAccount.getId().toString().equals(id));
        System.out.println("deleted Bank Account");

        return tempStorage.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BankAccount> bankAccountsIdGet(@PathVariable("id") String id) {
        System.out.println("searching for a bank account");
        for (BankAccount bankAccount : tempStorage) {
            if (bankAccount.getId().toString().equals(id)) {
                return new ResponseEntity<>(bankAccount, HttpStatus.OK);
            }
        }
        

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private BankAccount getRandomAccount(){
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(UUID.randomUUID());
        Transaction transaction = new Transaction();
        MonetaryAmount money = new MonetaryAmount();
        money.setAmount(Math.random());
        transaction.amount(money);
        bankAccount.addTransactionsItem(transaction);
        getMonetaryAmount(bankAccount);

        return bankAccount;
    }

    private void getMonetaryAmount(BankAccount bankAccount){
        MonetaryAmount monetaryAmount = new MonetaryAmount();
        double[] list = bankAccount.getTransactions().stream().map(transaction -> transaction.getAmount().getAmount()).mapToDouble(Double::doubleValue).toArray();
        double money = 0.0;
        for (double value : list) {
            money += value;
        }
        monetaryAmount.setAmount(money);
        bankAccount.setBalance(monetaryAmount);

    }

}
