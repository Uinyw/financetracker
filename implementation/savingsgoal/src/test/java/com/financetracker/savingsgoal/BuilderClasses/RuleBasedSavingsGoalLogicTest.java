package com.financetracker.savingsgoal.BuilderClasses;

import com.financetracker.savingsgoal.IntegrationTestBase;
import com.financetracker.savingsgoal.api.mapping.RuleBasedSavingsGoalMapper;
import com.financetracker.savingsgoal.infrastructure.db.RuleBasedSavingsGoalRepository;
import com.financetracker.savingsgoal.logic.model.*;
import com.financetracker.savingsgoal.logic.operations.PeriodicalSavingsGoalLogic;
import com.financetracker.savingsgoal.logic.operations.RuleBasedSavingsGoalLogic;
import com.financetracker.savingsgoal.logic.operations.RuleBasedSavingsGoalMatchingLogic;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.BankAccountDto;
import org.openapitools.client.model.MonetaryAmountDto;
import org.openapitools.model.RuleDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RuleBasedSavingsGoalLogicTest extends IntegrationTestBase {
    @Autowired
    private RuleBasedSavingsGoalLogic ruleBasedSavingsGoalLogic;

    @Autowired
    private RuleBasedSavingsGoalMatchingLogic ruleBasedSavingsGoalMatchingLogic;

    @Test
    void ruleBasedSavingsGoalTestIfMatch_ifexists_returnBoolean() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        UUID bankAccountId = UUID.randomUUID();

        RuleBasedSavingsGoal ruleBasedSavingsGoal = createRuleBasedSavingsGoal(bankAccountId,1.0,UUID.randomUUID());

        Class<?> clazz = RuleBasedSavingsGoalMatchingLogic.class;
        Method privateMethod = clazz.getDeclaredMethod("savingsGoalIsBasedOnBankAccount", RuleBasedSavingsGoal.class, UUID.class);
        privateMethod.setAccessible(true);
        boolean result = (boolean) privateMethod.invoke(ruleBasedSavingsGoalMatchingLogic, ruleBasedSavingsGoal, bankAccountId);

        assertThat(result, is(true));

    }
    @Test
    void ruleBasedSavingsGoalMatchRuleTest_ifNot_ThrowsException() throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = RuleBasedSavingsGoalMatchingLogic.class;
        Field repositoryField = clazz.getDeclaredField("repository");
        repositoryField.setAccessible(true);

        // Get the value of the private field from the instance
        RuleBasedSavingsGoalRepository repository = (RuleBasedSavingsGoalRepository) repositoryField.get(ruleBasedSavingsGoalMatchingLogic);

        UUID bankAccounntId = UUID.randomUUID();

        BankAccountDto bankAccountDto = createBankAccountDto(bankAccounntId, 2000);
        RuleBasedSavingsGoal ruleBasedSavingsGoal = createRuleBasedSavingsGoal(bankAccounntId, 1500, UUID.randomUUID());

        ruleBasedSavingsGoalMatchingLogic.checkForChanges(ruleBasedSavingsGoal);
        Boolean achieved = ruleBasedSavingsGoalMatchingLogic.isSavingsGoalAchieved(ruleBasedSavingsGoal, bankAccountDto);


        assertThat(true, is(achieved));

        ruleBasedSavingsGoalMatchingLogic.checkForChanges(ruleBasedSavingsGoal);

        var hue = repository.findAll().get(0);
        assertThat(true, is(achieved));


        ruleBasedSavingsGoal = createRuleBasedSavingsGoal(bankAccounntId, 2500, UUID.randomUUID(), MatchingType.MATCH_ALL, RuleType.LESS_THAN);
        ruleBasedSavingsGoalMatchingLogic.checkForChanges(ruleBasedSavingsGoal);
        achieved = ruleBasedSavingsGoalMatchingLogic.isSavingsGoalAchieved(ruleBasedSavingsGoal, bankAccountDto);
        assertThat(true, is(achieved));

        ruleBasedSavingsGoal = createRuleBasedSavingsGoal(bankAccounntId, 1000, UUID.randomUUID(), MatchingType.MATCH_ALL, RuleType.EQUALS);
        ruleBasedSavingsGoalMatchingLogic.checkForChanges(ruleBasedSavingsGoal);
        achieved = ruleBasedSavingsGoalMatchingLogic.isSavingsGoalAchieved(ruleBasedSavingsGoal, bankAccountDto);
        assertThat(false, is(achieved));
    }
    @Test
    void tmp() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Rule rule = createRule(RuleType.EQUALS, 100.0);
        double amount = 100.0;

        Method matchRuleType = RuleBasedSavingsGoalMatchingLogic.class.getDeclaredMethod("matchRuleType", Rule.class, double.class);
        matchRuleType.setAccessible(true);

        Boolean result = (Boolean) matchRuleType.invoke(ruleBasedSavingsGoalMatchingLogic, rule, amount);
        assertTrue(result);

        rule = createRule(RuleType.EQUALS, 100.0);
        amount = 99.0;
        result = (Boolean) matchRuleType.invoke(ruleBasedSavingsGoalMatchingLogic, rule, amount);
        assertTrue(!result);

        rule = createRule(RuleType.LESS_THAN, 100.0);
        amount = 99.0;
        result = (Boolean) matchRuleType.invoke(ruleBasedSavingsGoalMatchingLogic, rule, amount);
        assertTrue(result);

        rule = createRule(RuleType.LESS_THAN, 99.0);
        amount = 99.0;
        result = (Boolean) matchRuleType.invoke(ruleBasedSavingsGoalMatchingLogic, rule, amount);
        assertTrue(!result);

        rule = createRule(RuleType.GREATER_THAN, 99.0);
        amount = 99.0;
        result = (Boolean) matchRuleType.invoke(ruleBasedSavingsGoalMatchingLogic, rule, amount);
        assertTrue(!result);

        rule = createRule(RuleType.GREATER_THAN, 99.0);
        amount = 100.0;
        result = (Boolean) matchRuleType.invoke(ruleBasedSavingsGoalMatchingLogic, rule, amount);
        assertTrue(result);
    }
    @Test
    void ruleBasedSavingsGoal_whenRepositoryAction_thenExists(){
        final UUID id = UUID.randomUUID();
        RuleBasedSavingsGoal rbsg = createRuleBasedSavingsGoal(UUID.randomUUID(),1, id);

        ruleBasedSavingsGoalLogic.createRuleBasedSavingsGoal(rbsg);
        List<RuleBasedSavingsGoal> ruleBasedSavingsGoalList = ruleBasedSavingsGoalLogic.getRuleBasedSavingsGoals();

        int size = ruleBasedSavingsGoalList.size();
        assertThat(size, is(1));

        RuleBasedSavingsGoal returnedGoal = ruleBasedSavingsGoalLogic.findRuleBasedSavingsGoalById(id.toString());

        assertThat(rbsg.getDescription(), is(returnedGoal.getDescription()));
        assertThat(rbsg.getRules().size(), is(returnedGoal.getRules().size()));
        assertThat(rbsg.getMatchingType(), is(returnedGoal.getMatchingType()));
        assertThat(rbsg.getId(), is(returnedGoal.getId()));
        assertThat(rbsg.getName(), is(returnedGoal.getName()));
        assertThat(rbsg.getAchievementStatus(), is(returnedGoal.getAchievementStatus()));

        ruleBasedSavingsGoalLogic.deleteRuleBasedSavingsGoal(id.toString());
        ruleBasedSavingsGoalList =  ruleBasedSavingsGoalLogic.getRuleBasedSavingsGoals();

        size = ruleBasedSavingsGoalList.size();
        assertThat(size, is(0));
    }

    private RuleBasedSavingsGoal createRuleBasedSavingsGoal(UUID bankAccountId, double amount, UUID savingsGoalId){
        return createRuleBasedSavingsGoal(bankAccountId, amount, savingsGoalId, MatchingType.MATCH_ANY, RuleType.GREATER_THAN);
    }
    private RuleBasedSavingsGoal createRuleBasedSavingsGoal(UUID bankAccountId, double amount, UUID savingsGoalId, MatchingType type, RuleType ruleType){
        return RuleBasedSavingsGoal.with()
                .id(savingsGoalId)
                .rules(createRulesSet(bankAccountId, amount, savingsGoalId, ruleType))
                .achievementStatus(AchievementStatus.FAILED)
                .description("test description")
                .matchingType(type)
                .name("test name")
                .build();
    }

    private HashSet<Rule> createRulesSet(UUID bankAccountId, double amount, UUID rbsgId, RuleType ruleType){
        HashSet<Rule> ruleSet = new HashSet<>();
        ruleSet.add(createRule(bankAccountId, amount, rbsgId, ruleType));
        return ruleSet;
    }

    private Rule createRule(UUID id, double amount,UUID rbsgId, RuleType ruleType){
        return Rule.with()
                .savingsGoalId(rbsgId)
                .id(UUID.randomUUID())
                .bankAccountId(id)
                .description("description")
                .target(createMonetaryAmount(amount))
                .type(ruleType)
                .build();
    }

    private MonetaryAmount createMonetaryAmount(double number){
        MonetaryAmount monetaryAmount = new MonetaryAmount();
        monetaryAmount.setAmount(number);
        return monetaryAmount;
    }

    private BankAccountDto createBankAccountDto(UUID id, double amount){
        List<String> labelList = new ArrayList<>();
        labelList.add("new Label");

        return BankAccountDto.builder()
                .balance(createMonetaryAmountDto(amount))
                .id(id)
                .description("bank account description")
                .dispositionLimit(createMonetaryAmountDto(amount))
                .labels(labelList)
                .name("bank account name")
                .build();
    }

    private MonetaryAmountDto createMonetaryAmountDto(double number){
        MonetaryAmountDto monetaryAmountDto = new MonetaryAmountDto();
        monetaryAmountDto.setAmount(number);
        return monetaryAmountDto;
    }
    private Rule createRule(RuleType ruleType, double amount){
        return createRule(UUID.randomUUID(), amount,UUID.randomUUID(), ruleType);
    }
}
