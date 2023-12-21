package com.financetracker.transaction.infrastructure;

import com.financetracker.transaction.logic.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository<T extends Transaction> extends JpaRepository<T, String> {

}
