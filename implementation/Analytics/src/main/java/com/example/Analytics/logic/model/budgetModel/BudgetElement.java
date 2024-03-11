package com.example.Analytics.logic.model.budgetModel;

import com.example.Analytics.logic.model.generalModel.MonetaryAmount;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class BudgetElement {
    private Category category;
    private MonetaryAmount monetaryAmount;
}
