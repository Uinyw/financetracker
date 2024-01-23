package com.bank.account.logic.operations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bank.account.infrastructure.BankAccountRepository;
import com.bank.account.logic.model.BankAccount;
import com.bank.account.logic.model.MonetaryAmount;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.ApiException;
import org.openapitools.client.model.*;
import org.openapitools.model.BankAccountDto;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public List<BankAccount> getBankAccounts() {
        return bankAccountRepository.findAll();
    }

    public Optional<BankAccount> getBankAccount(final String bankAccountId) {
        return bankAccountRepository.findById(bankAccountId);
    }

    public void createBankAccount(final BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
    }

    public void updateBankAccount(final BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
    }


    public void deleteBankAccount(final String bankAccountId) {
        bankAccountRepository.deleteById(bankAccountId);
    }

    public void transfer(final OneTimeTransactionDto oneTimeTransactionDto) throws Exception {
        System.out.println("Transfering Money...");
        if (oneTimeTransactionDto.getTransferStatus().equals(TransferStatusDto.SUCCESSFUL)) {
            return;
        }
        if(oneTimeTransactionDto.getAmount() == null)
            return;

        TransferDto transferDto = oneTimeTransactionDto.getTransfer();

        if (transferDto.getSourceBankAccountId() != null) {
            withdrawFromBankAccount(transferDto.getSourceBankAccountId().toString(), oneTimeTransactionDto.getAmount().getAmount());
        }

        if (transferDto.getTargetBankAccountId() != null) {
            depositToBankAccount(transferDto.getTargetBankAccountId().toString(), oneTimeTransactionDto.getAmount().getAmount());
        }
    }

    private double getAvailableBalance(final BankAccount bankAccount) {
        final double balance = bankAccount.getBalance() != null && bankAccount.getBalance().amount() != null ? bankAccount.getBalance().amount().doubleValue() : 0.0;
        final double dispositionLimit = bankAccount.getDispositionLimit() != null && bankAccount.getDispositionLimit().amount() != null ? bankAccount.getDispositionLimit().amount().doubleValue() : 0.0;
        return balance + dispositionLimit;
    }

    private void withdrawFromBankAccount(final String bankAccountId, final double amount) throws Exception {
        final Optional<BankAccount> bankAccount = this.getBankAccount(bankAccountId);

        if (bankAccount.isEmpty()) {
            throw new Exception("Bank account does not exist.");
        }

        if (getAvailableBalance(bankAccount.get()) < amount) {
            throw new Exception("Bank account does not have sufficient balance.");
        }

        final boolean withdrawalSuccessful = this.updateBankAccountBalance(bankAccount.get(), amount);

        if (!withdrawalSuccessful) {
            throw new Exception("Withdrawal from bank account was not successful.");
        }
        System.out.println(String.format("withdraw Money... from {}\tto {}", bankAccountId, amount));
        System.out.println(bankAccount.get().toString());
    }

    private void depositToBankAccount(final String bankAccountId, final double amount) throws Exception {
        final Optional<BankAccount> bankAccount = this.getBankAccount(bankAccountId);

        if (bankAccount.isEmpty()) {
            throw new Exception("Bank account does not exist.");
        }

        final boolean depositSuccessful = this.updateBankAccountBalance(bankAccount.get(), amount);

        if (!depositSuccessful) {
            throw new Exception("Deposit to bank account was not successful.");
        }
        System.out.println(String.format("deposit Money... from {}\tto {}", bankAccountId, amount));
        System.out.println(bankAccount.get().toString());
    }


    public boolean updateBankAccountBalance(BankAccount bankAccount, double deltaAmount) {

        BigDecimal amount = BigDecimal.valueOf(bankAccount.getBalance().amount().doubleValue() + deltaAmount);
        final MonetaryAmount balance = new MonetaryAmount(amount);
        bankAccount.setBalance(balance);

        this.updateBankAccount(bankAccount);
        return true;
    }
}
