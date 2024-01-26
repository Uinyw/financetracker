package com.financetracker.product.infrastructure.client;

import com.financetracker.product.logic.model.MonetaryAmount;

import java.util.UUID;

public interface TransactionProvider {

    void bookExpense(final UUID bankAccountId, final MonetaryAmount amount);
}
