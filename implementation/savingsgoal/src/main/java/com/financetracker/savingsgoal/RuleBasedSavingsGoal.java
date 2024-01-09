package com.financetracker.savingsgoal;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.openapitools.model.AchievementStatus;

import java.util.Set;
import java.util.UUID;

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

    private Set<UUID> bankAccountIds;
    private Set<UUID> rules;
    private MatchingType matchingType;

    public Boolean applyRules() {
        switch (matchingType) {
            case MATCH_ALL:
                matchAllRules();
                break;
            case MATCH_ANY:
                matchAnyRules();
                break;
        }
        return null;
    }

    public Boolean matchAllRules(){
        Boolean returnValue = false;
        for (UUID rule : rules) {
            if(!checkRule(rule)){
                returnValue = false;
            }
        }
        return returnValue;
    }

    public Boolean matchAnyRules(){
        Boolean returnValue = false;
        for (UUID rule : rules) {
            if(checkRule(rule)){
                returnValue = true;
            }
        }
        return returnValue;
    }


    public Boolean checkRule(UUID ruleID){
        //TODO implement
        return null;
    }
}
