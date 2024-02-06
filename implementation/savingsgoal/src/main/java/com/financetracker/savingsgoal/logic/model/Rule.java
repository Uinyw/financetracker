package com.financetracker.savingsgoal.logic.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder(builderMethodName = "with")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rule")
@Entity
public class Rule {

    @Id
    private UUID id;

    @Column(name = "savings_goal_id")
    private UUID savingsGoalId;

    private UUID bankAccountId;

    private String description;

    private MonetaryAmount target;

    private RuleType type;
}
