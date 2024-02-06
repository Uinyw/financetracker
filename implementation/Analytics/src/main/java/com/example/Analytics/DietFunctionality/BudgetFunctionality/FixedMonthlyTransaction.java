package com.example.Analytics.DietFunctionality.BudgetFunctionality;

import com.example.Analytics.Type;
import lombok.Getter;
import org.openapitools.model.MonetaryAmount;

import java.util.List;
import java.util.UUID;

@Getter
public class FixedMonthlyTransaction {//transactions that are not shift-transactions
    private UUID id;
    private String name;
    private Transaction referenceTransaction;
    private Type type;
}