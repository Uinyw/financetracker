package com.financetracker.savingsgoal.BuilderClasses;

import com.financetracker.savingsgoal.IntegrationTestBase;
import com.financetracker.savingsgoal.infrastructure.db.RuleBasedSavingsGoalRepository;
import com.financetracker.savingsgoal.logic.model.*;
import com.financetracker.savingsgoal.logic.operations.PeriodicalSavingsGoalLogic;
import com.financetracker.savingsgoal.logic.operations.RuleBasedSavingsGoalLogic;
import com.financetracker.savingsgoal.logic.operations.RuleBasedSavingsGoalMatchingLogic;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.BankAccountDto;
import org.openapitools.client.model.MonetaryAmountDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RuleBasedSavingsGoalLogicTest extends IntegrationTestBase {
    @Autowired
    private RuleBasedSavingsGoalLogic ruleBasedSavingsGoalLogic;

    @Autowired
    private RuleBasedSavingsGoalMatchingLogic ruleBasedSavingsGoalMatchingLogic;

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
}
