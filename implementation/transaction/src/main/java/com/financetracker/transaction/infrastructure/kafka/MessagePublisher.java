package com.financetracker.transaction.infrastructure.kafka;

import com.financetracker.transaction.infrastructure.kafka.model.UpdateType;
import com.financetracker.transaction.logic.model.OneTimeTransaction;
import com.financetracker.transaction.logic.model.RecurringTransaction;


public interface MessagePublisher {

    void publishMessageOneTimeTransactionUpdate(final OneTimeTransaction oneTimeTransaction, final UpdateType updateType);
    void publishMessageRecurringTransactionUpdate(final RecurringTransaction recurringTransaction, final UpdateType updateType);
}
