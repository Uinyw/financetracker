package com.example.Analytics.budgetFunctionality.logic.model;

import com.example.Analytics.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Builder
@Table(name = "variable_monthly_transactions")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class VariableMonthlyTransaction {
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
}
