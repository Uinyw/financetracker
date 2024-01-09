package com.financetracker.transaction.infrastructure.db;

import com.financetracker.transaction.logic.model.RecurringTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecurringTransactionRepository extends JpaRepository<RecurringTransaction, String> {
}
