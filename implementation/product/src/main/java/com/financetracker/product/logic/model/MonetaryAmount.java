package com.financetracker.product.logic.model;

import java.math.BigDecimal;

public record MonetaryAmount(BigDecimal amount) {

    public static final MonetaryAmount DEFAULT = new MonetaryAmount(BigDecimal.ZERO);
}
