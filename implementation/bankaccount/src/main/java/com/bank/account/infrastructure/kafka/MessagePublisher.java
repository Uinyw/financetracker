package com.bank.account.infrastructure.kafka;

import com.bank.account.logic.model.BankAccount;

public interface MessagePublisher {

    void publishMessageBankAccountUpdate(final BankAccount bankAccount);
}
