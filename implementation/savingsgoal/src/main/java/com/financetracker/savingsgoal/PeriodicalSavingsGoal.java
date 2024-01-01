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

    private Periodicity periodicity;

    private Type type;



    public AchievmentStatus retryExecution(UUID recordId) {
        //TODO implement
        return AchievmentStatus.FAILED;
    }

    public void recalculateRecurringRate() {

    }

    public void recalculateRecurringAmount() {

    }

    public UUID getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(UUID bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public double getRecurringRate() {
        return recurringRate;
    }

    public void setRecurringRate(double recurringRate) {
        this.recurringRate = recurringRate;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public List<SavingsRecord> getRecords() {
        return records;
    }

    public void setRecords(List<SavingsRecord> records) {
        this.records = records;
    }

    public MonetaryAmount getRecurringAmount() {
        return recurringAmount;
    }

    public void setRecurringAmount(MonetaryAmount recurringAmount) {
        this.recurringAmount = recurringAmount;
    }

    public MonetaryAmount getGoal() {
        return goal;
    }

    public void setGoal(MonetaryAmount goal) {
        this.goal = goal;
    }
}
