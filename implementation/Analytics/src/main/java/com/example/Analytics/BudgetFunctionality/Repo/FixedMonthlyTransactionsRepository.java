package com.example.Analytics.BudgetFunctionality.Repo;

import com.example.Analytics.BudgetFunctionality.FixedMonthlyTransaction;
import com.example.Analytics.BudgetFunctionality.VariableMonthlyTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FixedMonthlyTransactionsRepository extends JpaRepository<FixedMonthlyTransaction, String> {
}
