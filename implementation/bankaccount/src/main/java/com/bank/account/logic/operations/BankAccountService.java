package com.bank.account.logic.operations;

import java.util.List;
import java.util.Optional;

import com.bank.account.infrastructure.BankAccountRepository;
import com.bank.account.infrastructure.kafka.MessagePublisher;
import com.bank.account.logic.model.BankAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final MessagePublisher messagePublisher;

    public List<BankAccount> getBankAccounts() {
        return bankAccountRepository.findAll();
    }

    public Optional<BankAccount> getBankAccount(final String bankAccountId) {
        return bankAccountRepository.findById(bankAccountId);
    }

    public void createBankAccount(final BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
        messagePublisher.publishMessageBankAccountUpdate(bankAccount);
    }

    public void updateBankAccount(final BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
        messagePublisher.publishMessageBankAccountUpdate(bankAccount);
    }

    public void deleteBankAccount(final String bankAccountId) {
        bankAccountRepository.deleteById(bankAccountId);
    }

}
