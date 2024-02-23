package com.example.Analytics.budgetFunctionality.infrastructure.db;

import com.example.Analytics.budgetFunctionality.logic.model.FixedMonthlyTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FixedMonthlyTransactionsRepository extends JpaRepository<FixedMonthlyTransaction, String> {
}
