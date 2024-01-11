package com.financetracker.savingsgoal;

import jakarta.persistence.*;
import lombok.*;
import org.openapitools.model.AchievementStatus;
import org.openapitools.model.Periodicity;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @JoinColumn(name = "periodical_savings_goal_id", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SavingsRecord> records = new ArrayList<>();

    @Column(name = "periodicity")
    private Periodicity periodicity;

    @Column(name = "goal_type")
    private Type type;

    public AchievementStatus retryExecution(UUID recordId) {
        // TODO implement
        return AchievementStatus.FAILED;
    }

    public void recalculateRecurringRate() {
        // TODO implement
    }

    public void recalculateRecurringAmount() {
        // TODO implement
    }
}