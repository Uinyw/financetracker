package com.financetracker.savingsgoal.model;

import com.financetracker.savingsgoal.MatchingType;
import com.financetracker.savingsgoal.Rule;
import com.financetracker.savingsgoal.RuleBasedSavingsGoal;
import org.openapitools.model.AchievementStatus;
import org.openapitools.model.RuleBasedSavingsGoalDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class RuleBasedSavingsGoalMapper {

    public RuleBasedSavingsGoalDTO ruleBasedSavingsGoalEntityToDTO(RuleBasedSavingsGoal ruleBasedSavingsGoal){
        if(ruleBasedSavingsGoal==null)
            return new RuleBasedSavingsGoalDTO();

        RuleBasedSavingsGoalDTO.TypeEnum matchingType = RuleBasedSavingsGoalDTO.TypeEnum.ALL;
        switch (ruleBasedSavingsGoal.getMatchingType()){
            case MATCH_ALL -> matchingType = RuleBasedSavingsGoalDTO.TypeEnum.ANY;
            case MATCH_ANY -> matchingType = RuleBasedSavingsGoalDTO.TypeEnum.ALL;
        }

        return createRuleBasedSavingsGoalDTO(
                ruleBasedSavingsGoal.getId(),
                ruleBasedSavingsGoal.getName(),
                ruleBasedSavingsGoal.getDescription(),
                matchingType,
                ruleBasedSavingsGoal.getRules());
    }
    public RuleBasedSavingsGoal ruleBasedSavingsGoalDTOtoEntity(RuleBasedSavingsGoalDTO ruleBasedSavingsGoalDTO){

        MatchingType typeEnum = MatchingType.MATCH_ALL;
        switch (ruleBasedSavingsGoalDTO.getType()){
            case ALL -> typeEnum = MatchingType.MATCH_ALL;
            case ANY -> typeEnum = MatchingType.MATCH_ANY;
        }

        RuleBasedSavingsGoal ruleBasedSavingsGoal = createRuleBasedSavingsGoal(
                ruleBasedSavingsGoalDTO.getId(),
                ruleBasedSavingsGoalDTO.getDescription(),
                ruleBasedSavingsGoalDTO.getName(),
                ruleBasedSavingsGoalDTO.getAchievementStatus(),
                convertRuleDTOToModel(ruleBasedSavingsGoalDTO.getRules()),
                typeEnum
        );

        return ruleBasedSavingsGoal;
    }

    private static RuleBasedSavingsGoalDTO createRuleBasedSavingsGoalDTO(UUID uuid, String name, String description, RuleBasedSavingsGoalDTO.TypeEnum typeEnum, List<Rule> rules) {
        RuleBasedSavingsGoalDTO ruleBasedSavingsGoalDTO = new RuleBasedSavingsGoalDTO();
        ruleBasedSavingsGoalDTO.setId(uuid);
        ruleBasedSavingsGoalDTO.setName(name);
        ruleBasedSavingsGoalDTO.setDescription(description);
        ruleBasedSavingsGoalDTO.setType(typeEnum);
        ruleBasedSavingsGoalDTO.setRules(mapModelToDTO(rules));
        return ruleBasedSavingsGoalDTO;
    }

    private static List<org.openapitools.model.Rule> mapModelToDTO(List<Rule> rules){
        List<org.openapitools.model.Rule> ruleList = new ArrayList<>();
        for(Rule rule : rules){
            org.openapitools.model.Rule tmpRule = new org.openapitools.model.Rule();
            tmpRule.setId(rule.getId());
            tmpRule.setDescription(rule.getDescription());
            tmpRule.setTarget(SavingsGoalMapper.monetaryAmountModeltoDTO(rule.getTarget()));
            tmpRule.setBankAccountID(rule.getBankAccountID());
            ruleList.add(tmpRule);
        }
        return ruleList;
    }

    private static RuleBasedSavingsGoal createRuleBasedSavingsGoal(UUID id, String description, String name, AchievementStatus achievementStatus, List<Rule> rules, MatchingType matchingType){
        RuleBasedSavingsGoal ruleBasedSavingsGoal = new RuleBasedSavingsGoal();
        ruleBasedSavingsGoal.setId(id);
        ruleBasedSavingsGoal.setDescription(description);
        ruleBasedSavingsGoal.setName(name);
        ruleBasedSavingsGoal.setAchievementStatus(achievementStatus);
        ruleBasedSavingsGoal.setRules(rules);
        ruleBasedSavingsGoal.setMatchingType(matchingType);

        return ruleBasedSavingsGoal;
    }

    private static List<Rule> convertRuleDTOToModel(List<org.openapitools.model.Rule> ruleList){
        List<Rule> newRuleList = new ArrayList<>();

        for (org.openapitools.model.Rule rule : ruleList){
            Rule newRule = new Rule();
            newRule.setBankAccountID(rule.getBankAccountID());
            newRule.setTarget(SavingsGoalMapper.monetaryAmountDTOtoModel(rule.getTarget()));
            newRule.setDescription(rule.getDescription());
            newRule.setId(rule.getId());
        }

        return newRuleList;
    }
}
