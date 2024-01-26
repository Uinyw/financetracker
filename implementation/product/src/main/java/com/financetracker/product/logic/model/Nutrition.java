package com.financetracker.product.logic.model;

import java.math.BigDecimal;

public record Nutrition (BigDecimal servingSize,
                         BigDecimal calories,
                         BigDecimal carbohydrates,
                         BigDecimal protein,
                         BigDecimal fat,
                         BigDecimal sugar) {

}
