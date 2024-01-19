package com.financetracker.savingsgoal;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.client.model.BankAccountDto;

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
