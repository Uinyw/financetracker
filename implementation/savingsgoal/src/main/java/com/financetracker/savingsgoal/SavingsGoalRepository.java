package com.financetracker.savingsgoal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financetracker.savingsgoal.model.SavingsGoalEntity;

@Repository
public interface SavingsGoalRepository extends JpaRepository<SavingsGoalEntity, Long>{

}