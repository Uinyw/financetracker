package com.financetracker.savingsgoal.model;

import com.financetracker.savingsgoal.RuleBasedSavingsGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RuleBasedSavingsGoalRepository extends JpaRepository<RuleBasedSavingsGoal, UUID> {

}
