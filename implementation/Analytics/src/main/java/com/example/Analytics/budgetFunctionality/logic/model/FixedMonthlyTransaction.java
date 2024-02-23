package com.example.Analytics.budgetFunctionality.logic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Table(name = "fixed_monthly_transaction")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FixedMonthlyTransaction {//transactions that are not shift-transactions
    @Id
    private UUID id;
    private String name;

    @OneToOne
    private Transaction referenceTransaction;

    private Type type;
}