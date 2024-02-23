package com.financetracker.savingsgoal.logic.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
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
    private UUID id;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @Column(name = "achievement_status")
    private com.financetracker.savingsgoal.logic.model.AchievementStatus achievementStatus;

    @Column(name = "source_bank_account_id")
    private UUID sourceBankAccountId;

    @Column(name = "target_bank_account_id")
    private UUID targetBankAccountId;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "goal_amount"))
    private com.financetracker.savingsgoal.logic.model.MonetaryAmount goal;

    @Column(name = "recurring_rate")
    private Double recurringRate;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "recurring_amount_amount"))
    private com.financetracker.savingsgoal.logic.model.MonetaryAmount recurringAmount;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "start", column = @Column(name = "start_date")),
            @AttributeOverride(name = "end", column = @Column(name = "end_date"))
    })
    private com.financetracker.savingsgoal.logic.model.Duration duration;

    @Column(name = "periodicity")
    private com.financetracker.savingsgoal.logic.model.Periodicity periodicity;

    @Setter
    @JoinColumn(name = "savings_goal_id", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<com.financetracker.savingsgoal.logic.model.SavingsRecord> savingsRecords = new HashSet<>();

}