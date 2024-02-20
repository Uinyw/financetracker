package com.example.Analytics;

import com.example.Analytics.BankAccount;
import com.example.Analytics.BudgetFunctionality.Periodicity;
import org.openapitools.client.model.MonetaryAmount;

import java.util.UUID;

public class RecurringSavingsGoal {
    private UUID id;
    private MonetaryAmount fixedAmount;
    private int reoccuringRate;
    private BankAccount source;
    private BankAccount target;
    private Periodicity periodicity;
}
