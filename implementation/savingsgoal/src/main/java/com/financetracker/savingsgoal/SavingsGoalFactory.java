package com.financetracker.savingsgoal;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;



@Component
public class SavingsGoalFactory {

    public SavingsGoal createPercentageBasedSavingsGoal(){
        return null;
    }

    public SavingsGoal createAmountBasedSavingsGoal(){
        return null;
    }

    public SavingsGoal createPeriodicalSavingsGoal(UUID bankAccountID){
        SavingsGoal savingsGoal = new PeriodicalSavingsGoal();
        /*savingsGoal.setBankAccountID(bankAccountID);
        MonetaryAmount monetaryAmount = new MonetaryAmount();
        monetaryAmount.setAmount(0);
        savingsGoal.setAmount(monetaryAmount);
        savingsGoal.setRecurringRate(0.0);
        savingsGoal.setRecurringAmount(1.0);
        savingsGoal.setDuration("month");
        savingsGoal.setPeriodicity(Periodicity.MONTHLY);*/
        return savingsGoal;
    }

    public SavingsGoal createRuleBasedSavingsRule(){
        return null;

    }
}