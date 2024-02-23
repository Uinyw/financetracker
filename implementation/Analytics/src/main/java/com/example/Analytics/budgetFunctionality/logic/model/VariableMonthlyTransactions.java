package com.example.Analytics.budgetFunctionality.logic.model;

import com.example.Analytics.Category;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Builder
@Table(name = "variable_monthly_transactions")
@Entity
@Setter
public class VariableMonthlyTransactions {
    @Id
    private UUID id;
    @Column(name = "name")
    private String name;

    @OneToOne
    private Category category;
    @OneToMany
    private List<Transaction> referenceTransactions; //alle mit dieser Categorie
    @Column(name = "averageAmount")
    private double averageAmount;
    @Column(name = "type")
    private Type type;

    public VariableMonthlyTransactions() {

    }
    public VariableMonthlyTransactions(UUID id, String name, Category category, List<Transaction> transactions, double averageAmount, Type type) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.referenceTransactions = transactions;
        this.averageAmount = averageAmount;
        this.type = type;
    }



}
