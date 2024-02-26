package com.example.Analytics.logic.model.generalModel;

public class YearlyMonth {
    private int month;
    private int year;

    public YearlyMonth(int month, int year) {
        this.month = month;
        this.year = year;
    }

    public String getString() {
        return year + "-" + month;
    }
}
