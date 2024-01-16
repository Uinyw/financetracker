package com.financetracker.savingsgoal;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class MonetaryAmount {
    private double amount;
}
