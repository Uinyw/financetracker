package com.bank.account;

import org.openapitools.api.BankAccountsApi;
import org.openapitools.model.BankAccount;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class BankAccountResource implements BankAccountsApi {

    @Override
    public ResponseEntity<List<BankAccount>> bankAccountsGet() {
        var bankAccount = new BankAccount();
        bankAccount.setId(UUID.randomUUID());
        return new ResponseEntity<>(List.of(bankAccount), HttpStatus.OK);
    }


}
