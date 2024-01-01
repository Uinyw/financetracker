package com.financetracker.transaction.logic.operations;

import com.financetracker.transaction.api.exceptions.TransferFailedException;
import com.financetracker.transaction.infrastructure.client.BankAccountProvider;
import com.financetracker.transaction.logic.model.Transfer;
import com.financetracker.transaction.logic.model.TransferStatus;
import com.financetracker.transaction.logic.model.Transferable;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.BankAccountDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TransferService {

    private final BankAccountProvider bankAccountProvider;

    public boolean requiredBankAccountsDoNotExist(final Transfer transfer) {
        return (transfer.hasInternalSource() && bankAccountProvider.getBankAccount(transfer.getSourceBankAccountId()).isEmpty())
                || (transfer.hasInternalTarget() && bankAccountProvider.getBankAccount(transfer.getTargetBankAccountId()).isEmpty());
    }

    public void transfer(final Transfer transfer, final Transferable transferable) {
        if (transferable.getTransferStatus().equals(TransferStatus.SUCCESSFUL)) {
            return;
        }

        if (transfer.hasInternalSource()) {
            withdrawFromBankAccount(transfer.getSourceBankAccountId(), transferable.getAmount().amount());
        }

        if (transfer.hasInternalTarget()) {
            depositToBankAccount(transfer.getTargetBankAccountId(), transferable.getAmount().amount());
        }
    }

    public void rollbackTransfer(final Transfer transfer, final Transferable transferable) {
        if (!transferable.getTransferStatus().equals(TransferStatus.SUCCESSFUL)) {
            return;
        }

        if (transfer.hasInternalSource() && !transfer.hasInternalTarget()) {
            depositToBankAccount(transfer.getTargetBankAccountId(), transferable.getAmount().amount());
            return;
        }

        if (transfer.hasInternalTarget() && !transfer.hasInternalSource()) {
            withdrawFromBankAccount(transfer.getSourceBankAccountId(), transferable.getAmount().amount());
            return;
        }

        transfer(transfer.invert(), transferable);
    }

    private void withdrawFromBankAccount(final String bankAccountId, final BigDecimal amount) {
        final Optional<BankAccountDto> bankAccountDto = bankAccountProvider.getBankAccount(bankAccountId);

        if (bankAccountDto.isEmpty()) {
            throw new TransferFailedException("Bank account does not exist.");
        }

        if (getAvailableBalance(bankAccountDto.get()) < amount.doubleValue()) {
            throw new TransferFailedException("Bank account does not have sufficient balance.");
        }

        final boolean withdrawalSuccessful = bankAccountProvider.updateBankAccountBalance(bankAccountDto.get(), amount.negate().doubleValue());

        if (!withdrawalSuccessful) {
            throw new TransferFailedException("Withdrawal from bank account was not successful.");
        }
    }

    private void depositToBankAccount(final String bankAccountId, final BigDecimal amount) {
        final Optional<BankAccountDto> bankAccountDto = bankAccountProvider.getBankAccount(bankAccountId);

        if (bankAccountDto.isEmpty()) {
            throw new TransferFailedException("Bank account does not exist.");
        }

        final boolean depositSuccessful = bankAccountProvider.updateBankAccountBalance(bankAccountDto.get(), amount.doubleValue());

        if (!depositSuccessful) {
            throw new TransferFailedException("Deposit to bank account was not successful.");
        }
    }

    private double getAvailableBalance(final BankAccountDto bankAccountDto) {
        final double balance = bankAccountDto.getBalance() != null && bankAccountDto.getBalance().getAmount() != null ? bankAccountDto.getBalance().getAmount() : 0.0;
        final double dispositionLimit = bankAccountDto.getDispositionLimit() != null && bankAccountDto.getDispositionLimit().getAmount() != null ? bankAccountDto.getDispositionLimit().getAmount() : 0.0;
        return balance + dispositionLimit;
    }
}
