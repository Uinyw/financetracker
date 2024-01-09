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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "periodical_savings_goal")
@Entity
public class PeriodicalSavingsGoal{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "description")
    private String description;
    @Column(name = "name")
    private String name;
    @Column(name = "achievementStatus")
    private AchievementStatus achievementStatus;
    @Column(name = "bankAccountId")
    private UUID bankAccountId;
    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "goal"))
    private org.openapitools.model.MonetaryAmount goal;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "recurringRate"))
    private org.openapitools.model.MonetaryAmount recurringRate;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "recurringAmount"))
    private org.openapitools.model.MonetaryAmount recurringAmount;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "start", column = @Column(name = "start")),
            @AttributeOverride(name = "end", column = @Column(name = "end"))
    })
    private Duration duration;// inclusive infinity

    @Setter
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SavingsRecord> records = new ArrayList<>();

    @Column(name = "periodicity")
    private Periodicity periodicity;

    @Column(name = "Type")
    private Type type;



    public AchievmentStatus retryExecution(UUID recordId) {
        //TODO implement
        return AchievmentStatus.FAILED;
    }

    public void recalculateRecurringRate() {

    }

    public void recalculateRecurringAmount() {

    }

}
