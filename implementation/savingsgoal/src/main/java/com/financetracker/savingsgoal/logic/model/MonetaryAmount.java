package com.financetracker.savingsgoal.logic.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Getter
@Setter
public class MonetaryAmount {
    private Double amount;
}
