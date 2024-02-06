package com.financetracker.savingsgoal.logic.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Builder(builderMethodName = "with")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "savings_record")
@Entity
public class SavingsRecord {

    @Id
    private UUID id;

    @Column(name = "savings_goal_id")
    private UUID savingsGoalId;

    private LocalDate date;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "amount"))
    private MonetaryAmount amount;

    private AchievementStatus achievementStatus;
}
