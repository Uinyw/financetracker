package com.financetracker.savingsgoal;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

public class PeriodicalSavingsGoal extends SavingsGoal {
    private UUID bankAccountId;
    private double recurringRate;
    private Duration duration; // inclusive infinity
    private List<SavingsRecord> records;
    private MonetaryAmount recurringAmount;
    private MonetaryAmount goal;


    public AchievmentStatus retryExecution(UUID recordId) {
        //TODO implement
        return AchievmentStatus.FAILED;
    }

    public void recalculateRecurringRate() {

    }

    public void recalculateRecurringAmount() {

    }

}
