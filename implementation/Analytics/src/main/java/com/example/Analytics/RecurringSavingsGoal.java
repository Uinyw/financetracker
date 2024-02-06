package com.example.Analytics;

import com.example.Analytics.BankAccount;
import org.openapitools.model.MonetaryAmount;
import org.openapitools.model.Periodicity;

import java.util.UUID;

public class RecurringSavingsGoal {
    private UUID id;
    private MonetaryAmount fixedAmount;
    private int reoccuringRate;
    private BankAccount source;
    private BankAccount target;
    private Periodicity periodicity;
}
