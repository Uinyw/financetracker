package com.financetracker.savingsgoal;

import jakarta.persistence.*;
import lombok.*;
import org.openapitools.client.model.BankAccountDto;
import org.openapitools.model.AchievementStatus;

import java.util.List;
import java.util.UUID;

@Builder(builderMethodName = "with")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rule_based_savings_goal")
@Entity
public class RuleBasedSavingsGoal{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "description")
    private String description;
    @Column(name = "name")
    private String name;
    @Column(name = "achievementStatus")
    private AchievementStatus achievementStatus;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "rule_list", joinColumns = @JoinColumn(name = "entity_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "rule_id")),
            @AttributeOverride(name = "bankAccountID", column = @Column(name = "rule_bank_account_id")),
            @AttributeOverride(name = "description", column = @Column(name = "rule_description"))
    })
    private List<Rule> rules;


    @Column(name = "matchingType")
    private MatchingType matchingType;
}
