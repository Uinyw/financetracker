package com.financetracker.savingsgoal.MapperTest;

import com.financetracker.savingsgoal.RuleBasedSavingsGoal;
import com.financetracker.savingsgoal.RuleBasedSavingsGoalBuilder;
import com.financetracker.savingsgoal.TestSetup.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.model.AchievementStatus;
import org.openapitools.model.RuleBasedSavingsGoalDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;

public class RuleBasedSavingsGoalMapperTest extends IntegrationTest {
    @BeforeEach
    void setUp() throws Exception {
        doNothing().when(messagePublisher).sendScheduledMessage(any());
    }
    @Test
    void givenValidRuleBasedSavingsGoal_whenMappingToModel_thenNoExceptionIsThrownAndResultingModelIsValid() {
        final RuleBasedSavingsGoalDTO ruleBasedSavingsGoalDTO = RuleBasedSavingsGoalBuilder.buildWithDefaults();
        final RuleBasedSavingsGoal ruleBasedSavingsGoal = ruleBasedSavingsGoalMapper.ruleBasedSavingsGoalDTOtoEntity(ruleBasedSavingsGoalDTO);

        assertThat(ruleBasedSavingsGoal.getId(), is(ruleBasedSavingsGoalDTO.getId().toString()));
        assertThat(ruleBasedSavingsGoal.getName(), is(ruleBasedSavingsGoalDTO.getName()));
        assertThat(ruleBasedSavingsGoal.getDescription(), is(ruleBasedSavingsGoalDTO.getDescription()));
        assertThat(ruleBasedSavingsGoal.getRules(), is(ruleBasedSavingsGoalDTO.getRules()));
        assertThat(ruleBasedSavingsGoal.getMatchingType(), is(ruleBasedSavingsGoalDTO.getType()));
        assertThat(ruleBasedSavingsGoal.getAchievementStatus(), is(ruleBasedSavingsGoalDTO.getAchievementStatus()));
    }

    @Test
    void givenValidRuleBasedSavingsGoalWithOnlyRequiredProperties_whenMappingToModel_thenNoExceptionIsThrownAndResultingModelIsValid() {
        final RuleBasedSavingsGoalDTO ruleBasedSavingsGoalDTO = RuleBasedSavingsGoalBuilder.build(
                "test",
                UUID.randomUUID(),
                null,
                null,
                null,
                null
        );

        final RuleBasedSavingsGoal ruleBasedSavingsGoal = ruleBasedSavingsGoalMapper.ruleBasedSavingsGoalDTOtoEntity(ruleBasedSavingsGoalDTO);

        assertThat(ruleBasedSavingsGoal.getId(), is(ruleBasedSavingsGoalDTO.getId().toString()));
        assertThat(ruleBasedSavingsGoal.getName(), is(ruleBasedSavingsGoalDTO.getName()));
        assertThat(ruleBasedSavingsGoal.getDescription(), is(ruleBasedSavingsGoalDTO.getDescription()));
        assertThat(ruleBasedSavingsGoal.getRules(), is(ruleBasedSavingsGoalDTO.getRules()));
        assertThat(ruleBasedSavingsGoal.getMatchingType(), is(ruleBasedSavingsGoalDTO.getType()));
        assertThat(ruleBasedSavingsGoal.getAchievementStatus(), is(ruleBasedSavingsGoalDTO.getAchievementStatus()));
    }


}
