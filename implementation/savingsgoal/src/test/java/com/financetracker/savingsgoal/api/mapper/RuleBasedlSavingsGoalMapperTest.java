package com.financetracker.savingsgoal.api.mapper;

import com.financetracker.savingsgoal.IntegrationTestBase;
import com.financetracker.savingsgoal.api.mapping.PeriodicalSavingsGoalMapper;
import com.financetracker.savingsgoal.api.mapping.RuleBasedSavingsGoalMapper;
import com.financetracker.savingsgoal.logic.model.*;
import org.junit.jupiter.api.Test;
import org.openapitools.model.MatchingTypeDto;
import org.openapitools.model.PeriodicalSavingsGoalDto;
import org.openapitools.model.RuleDto;
import org.openapitools.model.RuleTypeDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RuleBasedlSavingsGoalMapperTest extends IntegrationTestBase {
    @Autowired
    private RuleBasedSavingsGoalMapper ruleBasedSavingsGoalMapper;



    @Test
    void achievementStatusModelToDto_whenMapped_isEqual() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MatchingType matchingType = MatchingType.MATCH_ANY;

        Class<?> clazz = RuleBasedSavingsGoalMapper.class;
        Method achievementStatusModelToDto = clazz.getDeclaredMethod("achievementStatusModelToDto", MatchingType.class);
        achievementStatusModelToDto.setAccessible(true);
        Method achievementStatusDtoToModel = clazz.getDeclaredMethod("achievementStatusDtoToModel", MatchingTypeDto.class);
        achievementStatusDtoToModel.setAccessible(true);

        MatchingTypeDto resultmatchingType = (MatchingTypeDto) achievementStatusModelToDto.invoke(ruleBasedSavingsGoalMapper, matchingType);
        MatchingType newMatchingType = (MatchingType) achievementStatusDtoToModel.invoke(ruleBasedSavingsGoalMapper, resultmatchingType);

        assertThat(newMatchingType, is (matchingType));

        matchingType = MatchingType.MATCH_ANY;
        resultmatchingType = (MatchingTypeDto) achievementStatusModelToDto.invoke(ruleBasedSavingsGoalMapper, matchingType);
        newMatchingType = (MatchingType) achievementStatusDtoToModel.invoke(ruleBasedSavingsGoalMapper, resultmatchingType);
        assertThat(newMatchingType, is (matchingType));
    }

    @Test
    void ruleTypeModelToDto_whenMapped_isEqual() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        RuleType ruleType = RuleType.LESS_THAN;

        Class<?> clazz = RuleBasedSavingsGoalMapper.class;
        Method ruleTypeModelToDto = clazz.getDeclaredMethod("ruleTypeModelToDto", RuleType.class);
        ruleTypeModelToDto.setAccessible(true);
        Method ruleTypeDtoToModel = clazz.getDeclaredMethod("ruleTypeDtoToModel", RuleTypeDto.class);
        ruleTypeDtoToModel.setAccessible(true);

        RuleTypeDto resultmatchingType = (RuleTypeDto) ruleTypeModelToDto.invoke(ruleBasedSavingsGoalMapper, ruleType);
        RuleType newDuration = (RuleType) ruleTypeDtoToModel.invoke(ruleBasedSavingsGoalMapper, resultmatchingType);

        assertThat(newDuration, is (ruleType));

        ruleType = RuleType.EQUALS;
        resultmatchingType = (RuleTypeDto) ruleTypeModelToDto.invoke(ruleBasedSavingsGoalMapper, ruleType);
        newDuration = (RuleType) ruleTypeDtoToModel.invoke(ruleBasedSavingsGoalMapper, resultmatchingType);

        assertThat(newDuration, is (ruleType));

        ruleType = RuleType.GREATER_THAN;
        resultmatchingType = (RuleTypeDto) ruleTypeModelToDto.invoke(ruleBasedSavingsGoalMapper, ruleType);
        newDuration = (RuleType) ruleTypeDtoToModel.invoke(ruleBasedSavingsGoalMapper, resultmatchingType);

        assertThat(newDuration, is (ruleType));
    }

    @Test
    public void testRuleListModelToDto_whenMapped_isEqual() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Arrange
        Set<Rule> ruleSet = new HashSet<>();

        UUID savingsGoalId = UUID.randomUUID(); // Replace with your actual UUID creation logic
        Rule rule = Rule.with()
                .id(savingsGoalId)
                .bankAccountId(savingsGoalId)
                .savingsGoalId(savingsGoalId)
                .description("test description")
                .target(new MonetaryAmount(10.0))
                .type(RuleType.EQUALS)
                .build();
        ruleSet.add(rule);


        Method ruleListModelToDto = RuleBasedSavingsGoalMapper.class.getDeclaredMethod("ruleListModelToDto", Set.class);
        ruleListModelToDto.setAccessible(true);
        Method ruleListDtoToModel = RuleBasedSavingsGoalMapper.class.getDeclaredMethod("ruleListDtoToModel", List.class, UUID.class);
        ruleListDtoToModel.setAccessible(true);

        List<RuleDto> result = (List<RuleDto>) ruleListModelToDto.invoke(ruleBasedSavingsGoalMapper, ruleSet);
        Set<Rule> resultSet = (Set<Rule>) ruleListDtoToModel.invoke(ruleBasedSavingsGoalMapper, result, savingsGoalId);

        assertThat(resultSet.size(), is(ruleSet.size()));
        Rule resultRule = resultSet.stream().toList().get(0);
        assertThat(resultRule.getSavingsGoalId(), is(rule.getSavingsGoalId()));
        assertThat(resultRule.getId(), is(rule.getId()));
        assertThat(resultRule.getDescription(), is(rule.getDescription()));
        assertThat(resultRule.getBankAccountId(), is(rule.getBankAccountId()));
        assertThat(resultRule.getType(), is(rule.getType()));
        assertThat(resultRule.getTarget().getAmount(), is(rule.getTarget().getAmount()));
    }
}
