package com.financetracker.savingsgoal.logic.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Getter
@Setter
public class MonetaryAmount {
    private Double amount;
}
