package com.bank.account;

import org.openapitools.model.BankAccount;
import org.openapitools.model.MonetaryAmount;

import com.bank.account.model.BankAccountEntity;

public final class BankAccountMapper {

    /**
     * Converts a BankAccount to a BankAccountEntity
     * 
     * @param bankAccount
     * @return BankAccountEntity
     */
    public static BankAccountEntity BankAccountToEntity(BankAccount bankAccount) {
        return new BankAccountEntity(bankAccount.getId(), bankAccount.getName(), bankAccount.getDescription(),
                bankAccount.getBalance().getAmount(), bankAccount.getDispoLimit().getAmount(), null);
    }

    /**
     * Converts a BankAccountEntity to a BankAccount
     * 
     * @param bankAccountEntity
     * @return BankAccount
     */
    public static BankAccount EntityToBankAccount(BankAccountEntity bankAccountEntity) {
        MonetaryAmount balance = new MonetaryAmount();
        balance.setAmount(bankAccountEntity.getBalance());

        return new BankAccount().id(bankAccountEntity.getId()).name(bankAccountEntity.getName())
                .description(bankAccountEntity.getDescription()).balance(balance).dispoLimit(null).labels(null)
                .transactions(null);
    }

}
