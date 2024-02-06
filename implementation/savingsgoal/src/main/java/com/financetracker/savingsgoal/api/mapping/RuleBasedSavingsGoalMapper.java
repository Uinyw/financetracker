package com.financetracker.savingsgoal.api.mapping;

import com.financetracker.savingsgoal.logic.model.*;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.MatchingTypeDto;
import org.openapitools.model.RuleBasedSavingsGoalDto;
import org.openapitools.model.RuleDto;
import org.openapitools.model.RuleTypeDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class RuleBasedSavingsGoalMapper {

    private final CommonMapper commonMapper;

    public RuleBasedSavingsGoalDto ruleBasedSavingsGoalEntityToDto(RuleBasedSavingsGoal ruleBasedSavingsGoal) {
        return RuleBasedSavingsGoalDto.builder()
                .id(ruleBasedSavingsGoal.getId())
                .name(ruleBasedSavingsGoal.getName())
                .description(ruleBasedSavingsGoal.getDescription())
                .achievementStatus(commonMapper.achievementStatusModelToDto(ruleBasedSavingsGoal.getAchievementStatus()))
                .matchingType(achievementStatusModelToDto(ruleBasedSavingsGoal.getMatchingType()))
                .rules(ruleListModelToDto(ruleBasedSavingsGoal.getRules()))
                .build();
    }

    public RuleBasedSavingsGoal ruleBasedSavingsGoalDtoToEntity(RuleBasedSavingsGoalDto ruleBasedSavingsGoalDto){
        return RuleBasedSavingsGoal.with()
                .id(ruleBasedSavingsGoalDto.getId())
                .name(ruleBasedSavingsGoalDto.getName())
                .description(ruleBasedSavingsGoalDto.getDescription())
                .achievementStatus(commonMapper.achievementStatusDtoToModel(ruleBasedSavingsGoalDto.getAchievementStatus()))
                .matchingType(achievementStatusDtoToModel(ruleBasedSavingsGoalDto.getMatchingType()))
                .rules(ruleListDtoToModel(ruleBasedSavingsGoalDto.getRules(), ruleBasedSavingsGoalDto.getId()))
                .build();
    }

    public MatchingTypeDto achievementStatusModelToDto(final MatchingType matchingType) {
        return switch (matchingType) {
            case MATCH_ANY -> MatchingTypeDto.ANY;
            case MATCH_ALL -> MatchingTypeDto.ALL;
        };
    }

    public MatchingType achievementStatusDtoToModel(final MatchingTypeDto matchingTypeDto) {
        return switch (matchingTypeDto) {
            case ANY -> MatchingType.MATCH_ANY;
            case ALL -> MatchingType.MATCH_ALL;
        };
    }


    private List<RuleDto> ruleListModelToDto(final Set<Rule> rules) {
        return rules.stream()
                .map(rule -> RuleDto.builder()
                        .id(rule.getId())
                        .bankAccountId(rule.getBankAccountId())
                        .target(commonMapper.monetaryAmountModelToDto(rule.getTarget()))
                        .type(ruleTypeModelToDto(rule.getType()))
                        .description(rule.getDescription())
                        .build())
                .toList();
    }

    private Set<Rule> ruleListDtoToModel(final List<RuleDto> rules, final UUID savingsGoalId) {
        return rules.stream()
                .map(rule -> Rule.with()
                        .id(rule.getId())
                        .savingsGoalId(savingsGoalId)
                        .bankAccountId(rule.getBankAccountId())
                        .target(commonMapper.monetaryAmountDtoToModel(rule.getTarget()))
                        .type(ruleTypeDtoToModel(rule.getType()))
                        .description(rule.getDescription())
                        .build())
                .collect(Collectors.toSet());
    }

    public RuleTypeDto ruleTypeModelToDto(final RuleType ruleType) {
        return switch (ruleType) {
            case EQUALS -> RuleTypeDto.EQUALS;
            case GREATER_THAN -> RuleTypeDto.GREATER_THAN;
            case LESS_THAN -> RuleTypeDto.LESS_THAN;
        };
    }

    public RuleType ruleTypeDtoToModel(final RuleTypeDto ruleTypeDto) {
        return switch (ruleTypeDto) {
            case EQUALS -> RuleType.EQUALS;
            case GREATER_THAN -> RuleType.GREATER_THAN;
            case LESS_THAN -> RuleType.LESS_THAN;
        };
    }

}
