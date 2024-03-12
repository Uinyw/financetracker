package com.example.Analytics.logic.model.generalModel;

public class AmountMonthsAgo {
    private int monthsAgo;
    private double amount;

    public AmountMonthsAgo(int months, double amount) {
        this.monthsAgo = months;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public int getMonthsAgo() {
        return monthsAgo;
    }
}
