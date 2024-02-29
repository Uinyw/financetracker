package com.financetracker.savingsgoal.BuilderClasses;

import com.financetracker.savingsgoal.IntegrationTestBase;
import com.financetracker.savingsgoal.infrastructure.db.PeriodicalSavingsGoalRepository;
import com.financetracker.savingsgoal.logic.model.*;
import com.financetracker.savingsgoal.logic.operations.PeriodicalSavingsGoalLogic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PeriodicalSavingsGoalsLogicTest extends IntegrationTestBase {

    @Autowired
    private PeriodicalSavingsGoalLogic periodicalSavingsGoalLogic;

    @Test
    void periodicalSavingsGoal_whenRepositoryAction_thenExists(){
        final UUID randomUUid =UUID.randomUUID();
        PeriodicalSavingsGoal periodicalSavingsGoal = createPeriodicalSavingsGoal(randomUUid, 2.0);

        periodicalSavingsGoalLogic.createPeriodicalSavingsGoal(periodicalSavingsGoal);
        List<PeriodicalSavingsGoal> periodicalSavingsGoalList =  periodicalSavingsGoalLogic.getPeriodicalSavingsGoals();

        int size = periodicalSavingsGoalList.size();
        assertThat(size, is(1));

        PeriodicalSavingsGoal returnedGoal = periodicalSavingsGoalLogic.findPeriodicalSavingsGoalById(randomUUid.toString());

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


        periodicalSavingsGoalLogic.deletePeriodicalSavingsGoal(randomUUid.toString());
        periodicalSavingsGoalList =  periodicalSavingsGoalLogic.getPeriodicalSavingsGoals();

        size = periodicalSavingsGoalList.size();
        assertThat(size, is(0));
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
