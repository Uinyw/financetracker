package com.example.Analytics.BudgetFunctionality;

import com.example.Analytics.Type;
import lombok.Getter;

import java.util.UUID;

@Getter
public class FixedMonthlyTransaction {//transactions that are not shift-transactions
    private UUID id;
    private String name;
    private Transaction referenceTransaction;
    private Type type;
}