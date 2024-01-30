package com.financetracker.savingsgoal.logic.model;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Embeddable
@Setter
@Getter
public class Rule {
    private UUID id;
    private UUID bankAccountID;
    private String description;
    private MonetaryAmount target;
    private RuleType ruleType;
}
