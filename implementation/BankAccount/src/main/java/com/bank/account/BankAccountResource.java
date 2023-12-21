package com.bank.account;

import lombok.RequiredArgsConstructor;
import org.openapitools.api.BankAccountsApi;
import org.openapitools.model.BankAccount;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class BankAccountResource implements BankAccountsApi {

    private final BankAccountService bankAccountService;

    @Override
    public ResponseEntity<List<BankAccount>> bankAccountsGet() {
        bankAccountService.createAndSaveRandomAccount();
        System.out.println("bank account created");

        return new ResponseEntity<>(bankAccountService.getBankAccounts(), HttpStatus.OK);
    }
    
    @Override
    public ResponseEntity<Void> bankAccountsPost(@Valid @RequestBody BankAccount bankAccount) {
        bankAccount.setId(UUID.randomUUID());
        bankAccountService.saveBankAccount(bankAccount);
        System.out.println("bank account saved");

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> bankAccountsIdDelete(@PathVariable("id") String id) {
        Boolean successful = bankAccountService.deleteBankAccountById(id);
        System.out.println("deleted Bank Account");

        return successful ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BankAccount> bankAccountsIdGet(@PathVariable("id") String id) {
        System.out.println("searching for a bank account");
        BankAccount bankAccount = bankAccountService.getBankAccountById(id);
        return bankAccount == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(bankAccount, HttpStatus.OK);
    }

}
