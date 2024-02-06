package com.financetracker.savingsgoal.logic.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder(builderMethodName = "with")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rule_based_savings_goal")
@Entity
public class RuleBasedSavingsGoal {

    @Id
    private UUID id;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @Column(name = "achievement_status")
    private AchievementStatus achievementStatus;

    @Setter
    @JoinColumn(name = "savings_goal_id", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Rule> rules = new HashSet<>();

    @Column(name = "matching_type")
    private MatchingType matchingType;

}
