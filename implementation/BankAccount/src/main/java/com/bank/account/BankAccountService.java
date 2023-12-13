package com.bank.account;

import java.util.UUID;

import com.bank.account.model.BankAccountEntity;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.BankAccount;
import org.openapitools.model.MonetaryAmount;
import org.openapitools.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class BankAccountService {

    private final BankAccountFactory bankAccountFactory;
    private final BankAccountRepository bankAccountRepository;

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
        final var x = BankAccountEntity.with()
                .id(1)
                .title("Test")
                .build();
        bankAccountRepository.save(x);

        return null;
        //return bankAccountRepository.saveAndFlush(bankAccount);
    }

    public List<BankAccountEntity> get() {
        return bankAccountRepository.findAll();

        //return null;
        //return bankAccountRepository.saveAndFlush(bankAccount);
    }

    public BankAccount getBankAccountById(UUID id) {
        //Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(id);
        //return optionalBankAccount.orElse(null);
        return null;
    }

    public List<BankAccount> getBankAccounts() {
        return null;
    }
}
