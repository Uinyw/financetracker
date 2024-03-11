package com.example.Analytics.infrastructure.db;


import com.example.Analytics.logic.model.budgetModel.VariableMonthlyTransaction;
import com.example.Analytics.logic.model.generalModel.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
}
