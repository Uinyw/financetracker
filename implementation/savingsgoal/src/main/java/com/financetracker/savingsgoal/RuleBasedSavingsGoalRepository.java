package com.financetracker.savingsgoal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleBasedSavingsGoalRepository extends JpaRepository<RuleBasedSavingsGoal, Long> {

}
