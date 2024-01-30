package com.financetracker.savingsgoal.logic.model;

import jakarta.persistence.*;
import lombok.*;
import org.openapitools.model.AchievementStatus;
import org.openapitools.model.Periodicity;

import java.util.List;
import java.util.UUID;

@Builder(builderMethodName = "with")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "periodical_savings_goal")
public class PeriodicalSavingsGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @Column(name = "achievement_status")
    private AchievementStatus achievementStatus;

    @Column(name = "source_bank_account_id")
    private UUID sourceBankAccountId;

    @Column(name = "target_bank_account_id")
    private UUID targetBankAccountId;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "goal_amount"))
    private MonetaryAmount goal;

    @Column(name = "recurring_rate")
    private Double recurringRate;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "recurring_amount_amount"))
    private MonetaryAmount recurringAmount;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "start", column = @Column(name = "start_date")),
            @AttributeOverride(name = "end", column = @Column(name = "end_date"))
    })
    private Duration duration; // inclusive infinity

    @Column(name = "periodicity")
    private Periodicity periodicity;

}