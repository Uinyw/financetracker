package com.financetracker.savingsgoal.infrastructure.db;

import com.financetracker.savingsgoal.logic.model.RuleBasedSavingsGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RuleBasedSavingsGoalRepository extends JpaRepository<RuleBasedSavingsGoal, UUID> {

}
