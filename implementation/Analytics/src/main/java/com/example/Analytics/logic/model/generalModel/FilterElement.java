package com.example.Analytics.logic.model.generalModel;

import com.example.Analytics.logic.model.budgetModel.Category;
import com.example.Analytics.logic.model.productModel.Duration;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
@Setter
public class FilterElement {
    List<Category> categoryList;
    List<BankAccount> bankAccountList;
    Duration duration;
}
