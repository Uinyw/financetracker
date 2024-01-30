package com.financetracker.savingsgoal;

import jakarta.persistence.*;
import lombok.*;
import org.openapitools.model.AchievementStatus;
import org.openapitools.model.Periodicity;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder(builderMethodName = "with")
@Entity
@Getter
@Setter
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

    @Column(name = "bank_account_id")
    private UUID bankAccountId;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "goal_amount"))
    private MonetaryAmount goal;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "recurring_rate_amount"))
    private MonetaryAmount recurringRate;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "recurring_amount_amount"))
    private MonetaryAmount recurringAmount;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "start", column = @Column(name = "start_date")),
            @AttributeOverride(name = "end", column = @Column(name = "end_date"))
    })
    private Duration duration; // inclusive infinity

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "periodical_savings_goal_id", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "transaction_id")
    private List<UUID> transactionIds;

    @Column(name = "periodicity")
    private Periodicity periodicity;

    @Column(name = "goal_type")
    private Type type;
}