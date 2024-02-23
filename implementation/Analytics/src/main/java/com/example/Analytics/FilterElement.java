package com.example.Analytics;

import com.example.Analytics.dietFunctionality.logic.model.Duration;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FilterElement {
    List<Category> categoryList;
    List<BankAccount> bankAccountList;
    Duration duration;
}
