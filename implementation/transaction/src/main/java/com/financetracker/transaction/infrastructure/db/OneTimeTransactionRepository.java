package com.financetracker.transaction.infrastructure.db;

import com.financetracker.transaction.logic.model.OneTimeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OneTimeTransactionRepository extends JpaRepository<OneTimeTransaction, String> {
}
