package com.financetracker.savingsgoal;

import java.util.Set;
import java.util.UUID;

public class RuleBasedSavingsGoal extends SavingsGoal {
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
