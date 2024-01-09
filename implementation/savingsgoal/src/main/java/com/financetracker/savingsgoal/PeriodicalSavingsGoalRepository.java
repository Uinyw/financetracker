package com.financetracker.savingsgoal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodicalSavingsGoalRepository extends JpaRepository<PeriodicalSavingsGoal, Long>{

}