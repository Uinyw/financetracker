package com.example.Analytics.DietFunctionality.Repo;

import com.example.Analytics.BudgetFunctionality.VariableMonthlyTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<VariableMonthlyTransactions, String> {
}
