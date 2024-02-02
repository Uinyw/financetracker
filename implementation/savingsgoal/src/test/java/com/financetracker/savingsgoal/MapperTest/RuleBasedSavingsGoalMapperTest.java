package com.financetracker.savingsgoal.MapperTest;

import com.financetracker.savingsgoal.logic.model.RuleBasedSavingsGoal;
import com.financetracker.savingsgoal.data.TestRuleBasedSavingsGoalBuilder;
import com.financetracker.savingsgoal.TestSetup.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openapitools.model.RuleBasedSavingsGoalDTO;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;

public class RuleBasedSavingsGoalMapperTest extends IntegrationTest {

    @BeforeEach
    void setUp() {
        doNothing().when(messageConsumer).listenBankAccountChange(any(), any());
    }

    @Disabled
    @Test
    void givenValidRuleBasedSavingsGoal_whenMappingToModel_thenNoExceptionIsThrownAndResultingModelIsValid() {
        final RuleBasedSavingsGoalDTO ruleBasedSavingsGoalDTO = TestRuleBasedSavingsGoalBuilder.buildWithDefaults();
        final RuleBasedSavingsGoal ruleBasedSavingsGoal = ruleBasedSavingsGoalMapper.ruleBasedSavingsGoalDtoToEntity(ruleBasedSavingsGoalDTO);

        assertThat(ruleBasedSavingsGoal.getId().toString(), is(ruleBasedSavingsGoalDTO.getId().toString()));
        assertThat(ruleBasedSavingsGoal.getName(), is(ruleBasedSavingsGoalDTO.getName()));
        assertThat(ruleBasedSavingsGoal.getDescription(), is(ruleBasedSavingsGoalDTO.getDescription()));
        assertThat(ruleBasedSavingsGoal.getRules(), is(ruleBasedSavingsGoalDTO.getRules()));
        assertThat(ruleBasedSavingsGoal.getMatchingType(), is(ruleBasedSavingsGoalDTO.getType()));
        assertThat(ruleBasedSavingsGoal.getAchievementStatus(), is(ruleBasedSavingsGoalDTO.getAchievementStatus()));
    }

    @Disabled
    @Test
    void givenValidRuleBasedSavingsGoalWithOnlyRequiredProperties_whenMappingToModel_thenNoExceptionIsThrownAndResultingModelIsValid() {
        final RuleBasedSavingsGoalDTO ruleBasedSavingsGoalDTO = TestRuleBasedSavingsGoalBuilder.build(
                "test",
                UUID.randomUUID(),
                null,
                null,
                null,
                null
        );

        final RuleBasedSavingsGoal ruleBasedSavingsGoal = ruleBasedSavingsGoalMapper.ruleBasedSavingsGoalDtoToEntity(ruleBasedSavingsGoalDTO);

        assertThat(ruleBasedSavingsGoal.getId(), is(ruleBasedSavingsGoalDTO.getId().toString()));
        assertThat(ruleBasedSavingsGoal.getName(), is(ruleBasedSavingsGoalDTO.getName()));
        assertThat(ruleBasedSavingsGoal.getDescription(), is(ruleBasedSavingsGoalDTO.getDescription()));
        assertThat(ruleBasedSavingsGoal.getRules(), is(ruleBasedSavingsGoalDTO.getRules()));
        assertThat(ruleBasedSavingsGoal.getMatchingType(), is(ruleBasedSavingsGoalDTO.getType()));
        assertThat(ruleBasedSavingsGoal.getAchievementStatus(), is(ruleBasedSavingsGoalDTO.getAchievementStatus()));
    }


}
