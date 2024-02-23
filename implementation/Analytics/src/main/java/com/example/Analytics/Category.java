package com.example.Analytics;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


public class Category {

    private String name;

    //@OneToMany(mappedBy = "category")
    //private List<BudgetPlanEntry> budgetPlanEntries;
}
