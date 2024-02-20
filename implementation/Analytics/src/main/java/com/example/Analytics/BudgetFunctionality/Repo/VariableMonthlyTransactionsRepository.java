package com.example.Analytics.BudgetFunctionality.Repo;

import com.example.Analytics.BudgetFunctionality.VariableMonthlyTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariableMonthlyTransactionsRepository extends JpaRepository<VariableMonthlyTransactions, String> {
}
