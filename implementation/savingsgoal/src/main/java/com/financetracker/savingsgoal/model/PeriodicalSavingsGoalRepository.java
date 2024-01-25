package com.financetracker.savingsgoal.model;

import com.financetracker.savingsgoal.PeriodicalSavingsGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PeriodicalSavingsGoalRepository extends JpaRepository<PeriodicalSavingsGoal, UUID>{

}