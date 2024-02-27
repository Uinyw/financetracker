package com.example.Analytics.logic.model.budgetModel;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.model.MonetaryAmount;


@Builder
@Getter
@Setter
public class BudgetElement {
    private Category category;
    private MonetaryAmount monetaryAmount;
}
