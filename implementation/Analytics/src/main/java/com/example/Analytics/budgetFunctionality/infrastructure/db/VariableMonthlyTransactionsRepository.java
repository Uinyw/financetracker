package com.example.Analytics.budgetFunctionality.infrastructure.db;

import com.example.Analytics.budgetFunctionality.logic.model.VariableMonthlyTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariableMonthlyTransactionsRepository extends JpaRepository<VariableMonthlyTransaction, String> {
}
