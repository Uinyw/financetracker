package com.financetracker.savingsgoal.models;

import com.financetracker.savingsgoal.IntegrationTestBase;
import com.financetracker.savingsgoal.logic.model.*;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RuleBasedSavingsGoalTest extends IntegrationTestBase {
    @Test
    void givenPeriodicalSavingsGoal_whenUpdate_thenExists(){
        UUID uuid = UUID.randomUUID();
        String description = "description";
        String name = "name";
        MatchingType matchingType = MatchingType.MATCH_ANY;
        Set<Rule> ruleSet = new HashSet<>();
        AchievementStatus achievementStatus = AchievementStatus.ACHIEVED;

        RuleBasedSavingsGoal ruleBasedSavingsGoal = RuleBasedSavingsGoal.with()
                .id(uuid)
                .description(description)
                .name(name)
                .matchingType(matchingType)
                .rules(ruleSet)
                .achievementStatus(achievementStatus)
                .build();

        uuid = UUID.randomUUID();
        description = "new description";
        name = "new name";
        matchingType = MatchingType.MATCH_ALL;
        ruleSet = new HashSet<>();
        achievementStatus = AchievementStatus.FAILED;

        ruleBasedSavingsGoal.setId(uuid);
        ruleBasedSavingsGoal.setDescription(description);
        ruleBasedSavingsGoal.setName(name);
        ruleBasedSavingsGoal.setMatchingType(matchingType);
        ruleBasedSavingsGoal.setRules(ruleSet);
        ruleBasedSavingsGoal.setAchievementStatus(achievementStatus);

        assertThat(ruleBasedSavingsGoal.getId(), is(uuid));
        assertThat(ruleBasedSavingsGoal.getRules(), is(ruleSet));
        assertThat(ruleBasedSavingsGoal.getName(), is(name));
        assertThat(ruleBasedSavingsGoal.getMatchingType(), is(matchingType));
        assertThat(ruleBasedSavingsGoal.getAchievementStatus(), is(achievementStatus));
        assertThat(ruleBasedSavingsGoal.getDescription(), is(description));
    }

}
