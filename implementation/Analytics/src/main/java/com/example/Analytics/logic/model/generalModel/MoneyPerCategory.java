package com.example.Analytics.logic.model.generalModel;

import com.example.Analytics.logic.model.budgetModel.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public
class MoneyPerCategory {
    private Category category;
    private double money;
    private double moneyToBeSaved;

    public MoneyPerCategory(Category category, double money) {
        this.category = category;
        this.money = money;
    }

}
