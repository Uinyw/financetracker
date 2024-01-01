package com.financetracker.savingsgoal;

import java.time.Duration;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;



@Component
public class SavingsGoalFactory {

    public SavingsGoal createPercentageBasedSavingsGoal(){return null;
    }

    public SavingsGoal createAmountBasedSavingsGoal(){
        return null;
    }

    public SavingsGoal createPeriodicalSavingsGoal(UUID bankAccountID){
        PeriodicalSavingsGoal savingsGoal = new PeriodicalSavingsGoal();
        savingsGoal.setBankAccountId(bankAccountID);
        savingsGoal.setRecurringRate(0.0);
        MonetaryAmount reoccuringAmount = new MonetaryAmount();
        reoccuringAmount.setAmount(1.0);
        savingsGoal.setRecurringAmount(reoccuringAmount);
        return savingsGoal;
    }

    public SavingsGoal createRuleBasedSavingsRule(){
        return null;
    }
}