package com.example.Analytics.logic.model.forecast;

import com.example.Analytics.logic.model.budgetModel.Periodicity;
import com.example.Analytics.logic.model.generalModel.BankAccount;
import com.example.Analytics.logic.model.generalModel.MonetaryAmount;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.UUID;

@Entity
public class RecurringSavingsGoal {
    @Id
    private UUID id;
    private MonetaryAmount fixedAmount;
    private double recurringRate;
    private Periodicity periodicity;
    private UUID bankAccountSource;
    private UUID bankAccountTarget;
}
