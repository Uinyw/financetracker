package com.example.Analytics.infrastructure.db;


import com.example.Analytics.logic.model.budgetModel.FixedMonthlyTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FixedTransactionRepository extends JpaRepository<FixedMonthlyTransaction, UUID> {
}
