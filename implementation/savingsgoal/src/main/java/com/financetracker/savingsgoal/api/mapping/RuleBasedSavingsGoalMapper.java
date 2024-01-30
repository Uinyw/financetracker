package com.financetracker.savingsgoal.api.mapping;

import com.financetracker.savingsgoal.logic.model.MatchingType;
import com.financetracker.savingsgoal.logic.model.MonetaryAmount;
import com.financetracker.savingsgoal.logic.model.Rule;
import com.financetracker.savingsgoal.logic.model.RuleBasedSavingsGoal;
import org.openapitools.model.RuleBasedSavingsGoalDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RuleBasedSavingsGoalMapper {

    public RuleBasedSavingsGoalDTO ruleBasedSavingsGoalEntityToDTO(RuleBasedSavingsGoal ruleBasedSavingsGoal) {
        RuleBasedSavingsGoalDTO.TypeEnum matchingType = switch (ruleBasedSavingsGoal.getMatchingType()) {
            case MATCH_ALL -> RuleBasedSavingsGoalDTO.TypeEnum.ANY;
            case MATCH_ANY -> RuleBasedSavingsGoalDTO.TypeEnum.ALL;
        };

        return RuleBasedSavingsGoalDTO.builder()
                .id(ruleBasedSavingsGoal.getId())
                .name(ruleBasedSavingsGoal.getName())
                .description(ruleBasedSavingsGoal.getDescription())
                .achievementStatus(ruleBasedSavingsGoal.getAchievementStatus())
                .type(matchingType)
                .rules(mapModelToDTO(ruleBasedSavingsGoal.getRules()))
                .build();
    }

    public RuleBasedSavingsGoal ruleBasedSavingsGoalDTOtoEntity(RuleBasedSavingsGoalDTO ruleBasedSavingsGoalDTO){
        MatchingType typeEnum = switch (ruleBasedSavingsGoalDTO.getType()){
            case ALL -> typeEnum = MatchingType.MATCH_ALL;
            case ANY -> typeEnum = MatchingType.MATCH_ANY;
        };

        return RuleBasedSavingsGoal.with()
                .id(ruleBasedSavingsGoalDTO.getId())
                .name(ruleBasedSavingsGoalDTO.getName())
                .description(ruleBasedSavingsGoalDTO.getDescription())
                .achievementStatus(ruleBasedSavingsGoalDTO.getAchievementStatus())
                .matchingType(typeEnum)
                .rules(convertRuleDTOToModel(ruleBasedSavingsGoalDTO.getRules()))
                .build();
    }

    private List<org.openapitools.model.Rule> mapModelToDTO(List<Rule> rules){
        List<org.openapitools.model.Rule> ruleList = new ArrayList<>();
        for(Rule rule : rules){
            org.openapitools.model.Rule tmpRule = org.openapitools.model.Rule.builder()
                    .id(rule.getId())
                    .description(rule.getDescription())
                    .target(org.openapitools.model.MonetaryAmount.builder().amount(rule.getTarget().getAmount()).build())
                    .bankAccountID(rule.getBankAccountID())
                    .build();
            ruleList.add(tmpRule);
        }
        return ruleList;
    }

    private List<Rule> convertRuleDTOToModel(List<org.openapitools.model.Rule> ruleList) {
        List<Rule> newRuleList = new ArrayList<>();

        for (org.openapitools.model.Rule rule : ruleList) {
            Rule newRule = new Rule();
            newRule.setBankAccountID(rule.getBankAccountID());
            newRule.setTarget(new MonetaryAmount(rule.getTarget().getAmount()));
            newRule.setDescription(rule.getDescription());
            newRule.setId(rule.getId());
        }

        return newRuleList;
    }


}
