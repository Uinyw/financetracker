package com.financetracker.product.infrastructure.kafka;

import com.financetracker.product.logic.model.Product;

import java.math.BigDecimal;

public interface MessagePublisher {

    void publishMessageProductUpdate(final Product product, final BigDecimal amount);
}
