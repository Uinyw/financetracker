package com.bank.account.api;

import com.bank.account.api.mapping.BankAccountMapper;
import com.bank.account.logic.model.BankAccount;
import com.bank.account.logic.operations.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.BankAccountsApi;
import org.openapitools.model.BankAccountDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class BankAccountResource implements BankAccountsApi {

    private final BankAccountService bankAccountService;
    private final BankAccountMapper bankAccountMapper;

    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<List<BankAccountDto>> bankAccountsGet() {
        return new ResponseEntity<>(bankAccountService.getBankAccounts().stream()
                .map(bankAccountMapper::mapBankAccountModelToDtp)
                .toList(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<Void> bankAccountsPost(final BankAccountDto bankAccountDto) {
        bankAccountService.createBankAccount(bankAccountMapper.mapBankAccountDtoToModel(bankAccountDto));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> bankAccountsIdDelete(final String id) {
        if (bankAccountService.getBankAccount(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        bankAccountService.deleteBankAccount(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<BankAccountDto> bankAccountsIdGet(final String id) {
        final Optional<BankAccount> bankAccount = bankAccountService.getBankAccount(id);
        return bankAccount.map(account -> new ResponseEntity<>(bankAccountMapper.mapBankAccountModelToDtp(account), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> bankAccountsIdPatch(final String id, final BankAccountDto bankAccountDto) {
        if (bankAccountService.getBankAccount(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        bankAccountService.updateBankAccount(bankAccountMapper.mapBankAccountDtoToModel(bankAccountDto));
        return ResponseEntity.ok().build();
    }

}
