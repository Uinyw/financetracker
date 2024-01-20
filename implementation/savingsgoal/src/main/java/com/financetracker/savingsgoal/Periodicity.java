package com.financetracker.savingsgoal;

import lombok.Getter;

@Getter
public enum Periodicity {
    MONTHLY(1),
    QUARTERLY(3),
    HALF_YEARLY(6),
    YEARLY(12);

    private final int months;

    Periodicity(final int months) {
        this.months = months;
    }
}
