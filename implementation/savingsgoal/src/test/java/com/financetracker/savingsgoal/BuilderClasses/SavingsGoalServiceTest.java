package com.financetracker.savingsgoal.BuilderClasses;

import com.financetracker.savingsgoal.IntegrationTestBase;
import com.financetracker.savingsgoal.logic.model.*;
import com.financetracker.savingsgoal.logic.operations.SavingsGoalService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.BankAccountDto;
import org.openapitools.client.model.MonetaryAmountDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SavingsGoalServiceTest extends IntegrationTestBase {

    @Autowired
    private SavingsGoalService savingsGoalService;

    @Test
    void givenPeriodicalSavingsGoal_whenMap_thenPeriodicalSavingsGoalExists(){
        final UUID id = UUID.randomUUID();
        PeriodicalSavingsGoal periodicalSavingsGoal = createPeriodicalSavingsGoal(id, 2.0);

        savingsGoalService.createPeriodicalSavingsGoal(periodicalSavingsGoal);
        PeriodicalSavingsGoal returnedGoal = savingsGoalService.getPeriodicalSavingsGoal(id.toString());

        assertThat(returnedGoal.getGoal().getAmount(), is(periodicalSavingsGoal.getGoal().getAmount()));
        assertThat(returnedGoal.getId(), is(periodicalSavingsGoal.getId()));
        assertThat(returnedGoal.getName(), is(periodicalSavingsGoal.getName()));
        assertThat(returnedGoal.getDuration().getEnd(), is(periodicalSavingsGoal.getDuration().getEnd()));
        assertThat(returnedGoal.getDuration().getStart(), is(periodicalSavingsGoal.getDuration().getStart()));
        assertThat(returnedGoal.getSavingsRecords().size(), is(periodicalSavingsGoal.getSavingsRecords().size()));
        assertThat(returnedGoal.getDescription(), is(periodicalSavingsGoal.getDescription()));
        assertThat(returnedGoal.getPeriodicity(), is(periodicalSavingsGoal.getPeriodicity()));
        assertThat(returnedGoal.getRecurringAmount().getAmount(), is(periodicalSavingsGoal.getRecurringAmount().getAmount()));
        assertThat(returnedGoal.getRecurringRate(), is(periodicalSavingsGoal.getRecurringRate()));
        assertThat(returnedGoal.getSourceBankAccountId(), is(periodicalSavingsGoal.getSourceBankAccountId()));
        assertThat(returnedGoal.getTargetBankAccountId(), is(periodicalSavingsGoal.getTargetBankAccountId()));

        PeriodicalSavingsGoal newPeriodicalSavingsGoal = createPeriodicalSavingsGoal(id, 3.0);
        savingsGoalService.updatePeriodicalSavingsGoal(id.toString(), newPeriodicalSavingsGoal);
        returnedGoal = savingsGoalService.getPeriodicalSavingsGoals().get(0);

        assertThat(returnedGoal.getGoal().getAmount(), is(newPeriodicalSavingsGoal.getGoal().getAmount()));
        assertThat(returnedGoal.getId(), is(newPeriodicalSavingsGoal.getId()));
        assertThat(returnedGoal.getName(), is(newPeriodicalSavingsGoal.getName()));
        assertThat(returnedGoal.getDuration().getEnd(), is(newPeriodicalSavingsGoal.getDuration().getEnd()));
        assertThat(returnedGoal.getDuration().getStart(), is(newPeriodicalSavingsGoal.getDuration().getStart()));
        assertThat(returnedGoal.getSavingsRecords().size(), is(newPeriodicalSavingsGoal.getSavingsRecords().size()));
        assertThat(returnedGoal.getDescription(), is(newPeriodicalSavingsGoal.getDescription()));
        assertThat(returnedGoal.getPeriodicity(), is(newPeriodicalSavingsGoal.getPeriodicity()));
        assertThat(returnedGoal.getRecurringAmount().getAmount(), is(newPeriodicalSavingsGoal.getRecurringAmount().getAmount()));
        assertThat(returnedGoal.getRecurringRate(), is(newPeriodicalSavingsGoal.getRecurringRate()));
        assertThat(returnedGoal.getSourceBankAccountId(), is(newPeriodicalSavingsGoal.getSourceBankAccountId()));
        assertThat(returnedGoal.getTargetBankAccountId(), is(newPeriodicalSavingsGoal.getTargetBankAccountId()));

        savingsGoalService.deletePeriodicalSavingsGoal(id.toString());
        var x = savingsGoalService.getPeriodicalSavingsGoals();
        assertThat(x.size(), is(0));
    }


    @Test
    void givenRuleBasedSavingsGoal_whenMap_thenRuleBasedSavingsGoalExists(){
        final UUID id = UUID.randomUUID();
        RuleBasedSavingsGoal rbsg = createRuleBasedSavingsGoal(id,1);

        savingsGoalService.createRuleBasedSavingsGoal(rbsg);
        RuleBasedSavingsGoal receivedGoal = savingsGoalService.getRuleBasedSavingsGoal(id.toString());

        assertThat(rbsg.getDescription(), is(receivedGoal.getDescription()));
        assertThat(rbsg.getRules().size(), is(receivedGoal.getRules().size()));
        assertThat(rbsg.getMatchingType(), is(receivedGoal.getMatchingType()));
        assertThat(rbsg.getId(), is(receivedGoal.getId()));
        assertThat(rbsg.getName(), is(receivedGoal.getName()));
        assertThat(rbsg.getAchievementStatus(), is(receivedGoal.getAchievementStatus()));

        rbsg = createRuleBasedSavingsGoal(id,2);
        savingsGoalService.updateRuleBasedSavingsGoal(id.toString(), rbsg);

        assertThat(rbsg.getDescription(), is(receivedGoal.getDescription()));
        assertThat(rbsg.getRules().size(), is(receivedGoal.getRules().size()));
        assertThat(rbsg.getMatchingType(), is(receivedGoal.getMatchingType()));
        assertThat(rbsg.getId(), is(receivedGoal.getId()));
        assertThat(rbsg.getName(), is(receivedGoal.getName()));
        assertThat(rbsg.getAchievementStatus(), is(receivedGoal.getAchievementStatus()));

        savingsGoalService.deleteRuleBasedSavingsGoal(id.toString());
        var size = savingsGoalService.getRuleBasedSavingsGoals().size();
        assertThat(size, is(0));
    }

    @Test
    void onReceiveBankAccountChange(){
        BankAccountDto bankAccountDto = createBankAccountDto(UUID.randomUUID(), 0.0);
        savingsGoalService.onReceiveBankAccountChange(bankAccountDto);
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

    @AfterEach
    void tearDown() {
        //productRepository.deleteAll();
    }

    private RuleBasedSavingsGoal createRuleBasedSavingsGoal(UUID uuid, double amount){
        return RuleBasedSavingsGoal.with()
                .id(uuid)
                .rules(createRulesSet(uuid, amount))
                .achievementStatus(AchievementStatus.ACHIEVED)
                .description("test description")
                .matchingType(MatchingType.MATCH_ANY)
                .name("test name")
                .build();
    }

    private HashSet<Rule> createRulesSet(UUID uuid, double amount){
        HashSet<Rule> ruleSet = new HashSet<>();
        ruleSet.add(createRule(uuid, amount));
        return ruleSet;
    }

    private Rule createRule(UUID id, double amount){
        return Rule.with()
                .savingsGoalId(id)
                .id(id)
                .bankAccountId(id)
                .description("description")
                .target(createMonetaryAmount(amount))
                .type(RuleType.EQUALS)
                .build();
    }

    private PeriodicalSavingsGoal createPeriodicalSavingsGoal(UUID uuid, double number){
        return PeriodicalSavingsGoal.with()
                .id(uuid)
                .goal(createMonetaryAmount(number))
                .periodicity(Periodicity.MONTHLY)
                .targetBankAccountId(uuid)
                .sourceBankAccountId(uuid)
                .savingsRecords(new HashSet<>())
                .achievementStatus(AchievementStatus.ACHIEVED)
                .name("Periodical Savings Goal")
                .description("description")
                .duration(createDuration(number))
                .recurringAmount(createMonetaryAmount(number))
                .recurringRate(2.0)
                .build();
    }


    private MonetaryAmount createMonetaryAmount(double number){
        MonetaryAmount monetaryAmount = new MonetaryAmount();
        monetaryAmount.setAmount(number);
        return monetaryAmount;
    }
    private Duration createDuration(double number){
        Duration duration = new Duration();
        duration.setEnd(LocalDate.now().plusMonths((int)number));
        duration.setStart(LocalDate.now().plusMonths((int)number));
        return duration;
    }

}
