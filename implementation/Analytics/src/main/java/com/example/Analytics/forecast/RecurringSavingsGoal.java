package com.example.Analytics.forecast;

import com.example.Analytics.budgetFunctionality.logic.model.Periodicity;
import jakarta.persistence.*;
import org.openapitools.client.model.MonetaryAmount;

import java.util.UUID;

@Table(name = "bank_account")
@Entity
public class RecurringSavingsGoal {
    @Id
    private UUID id;
    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "balance", precision = 27, scale = 6))
    private MonetaryAmount fixedAmount;
    private int reoccuringRate;
    private BankAccount source;
    private BankAccount target;
    private Periodicity periodicity;
}
