package com.financetracker.savingsgoal.BuilderClasses;

import com.financetracker.savingsgoal.IntegrationTestBase;
import com.financetracker.savingsgoal.logic.model.*;
import com.financetracker.savingsgoal.logic.operations.PeriodicalSavingsGoalTransferLogic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PeriodicalSavingsGoalTransferLogicTest extends IntegrationTestBase {

    @Autowired
    private PeriodicalSavingsGoalTransferLogic periodicalSavingsGoalTransferLogic;

    @Test
    void givenTransferObject_testTransferFunctions() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        UUID uuid = UUID.randomUUID();
        double number = 1.0;
        Set<SavingsRecord> savingsRecordSet = new HashSet<>();
        SavingsRecord savingsRecord = createSavingsRecords(uuid, number);

        PeriodicalSavingsGoal periodicalSavingsGoal = createPeriodicalSavingsGoal(uuid, number, savingsRecordSet, Periodicity.MONTHLY);
        LocalDate date = LocalDate.now();

        Class<?> clazz = PeriodicalSavingsGoalTransferLogic.class;
        Method isFinishedMethod  = clazz.getDeclaredMethod("isFinished", PeriodicalSavingsGoal.class, LocalDate.class);
        Method setSavingGoalStatusMethod = clazz.getDeclaredMethod("setSavingGoalStatus", PeriodicalSavingsGoal.class);
        Method isDueMethod = clazz.getDeclaredMethod("isDue", PeriodicalSavingsGoal.class, LocalDate.class);
        Method transferSavingsGoalMethod = clazz.getDeclaredMethod("transferSavingsGoal", PeriodicalSavingsGoal.class);

        isFinishedMethod.setAccessible(true);
        setSavingGoalStatusMethod.setAccessible(true);
        isDueMethod.setAccessible(true);
        transferSavingsGoalMethod.setAccessible(true);

        boolean isFinishedResult = (boolean) isFinishedMethod.invoke(periodicalSavingsGoalTransferLogic, periodicalSavingsGoal, date);
        setSavingGoalStatusMethod.invoke(periodicalSavingsGoalTransferLogic, periodicalSavingsGoal);
        boolean isDueResult = (boolean) isDueMethod.invoke(periodicalSavingsGoalTransferLogic, periodicalSavingsGoal, date);
        transferSavingsGoalMethod.invoke(periodicalSavingsGoalTransferLogic, periodicalSavingsGoal);

        assertThat(isFinishedResult, is(false));
        assertThat(isDueResult, is(false));

        periodicalSavingsGoal = createPeriodicalSavingsGoal(uuid, number, savingsRecordSet, Periodicity.MONTHLY, createDuration(date, date.plusMonths(1)));
        isFinishedResult = (boolean) isFinishedMethod.invoke(periodicalSavingsGoalTransferLogic, periodicalSavingsGoal, date.plusDays(1));
        isDueResult = (boolean) isDueMethod.invoke(periodicalSavingsGoalTransferLogic, periodicalSavingsGoal, date.plusDays(1));

        assertThat(isFinishedResult, is(false));
        assertThat(isDueResult, is(false));

        periodicalSavingsGoal = createPeriodicalSavingsGoal(uuid, number, savingsRecordSet, Periodicity.MONTHLY, createDuration(date.minusMonths(1), date));
        isFinishedResult = (boolean) isFinishedMethod.invoke(periodicalSavingsGoalTransferLogic, periodicalSavingsGoal, date.plusYears(1));
        isDueResult = (boolean) isDueMethod.invoke(periodicalSavingsGoalTransferLogic, periodicalSavingsGoal, date);

        assertThat(isFinishedResult, is(false));
        assertThat(isDueResult, is(false));

    }
    @Test
    void sss() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Class<?> clazz = PeriodicalSavingsGoalTransferLogic.class;
        Method isFinishedMethod  = clazz.getDeclaredMethod("isFinished", PeriodicalSavingsGoal.class, LocalDate.class);
        Method isDueMethod = clazz.getDeclaredMethod("isDue", PeriodicalSavingsGoal.class, LocalDate.class);

        isFinishedMethod.setAccessible(true);
        isDueMethod.setAccessible(true);
        LocalDate monthAgo = LocalDate.now().minusMonths(1);
        boolean isFinishedResult = (boolean) isFinishedMethod.invoke(periodicalSavingsGoalTransferLogic, PeriodicalSavingsGoal.with().duration(createDuration(monthAgo,monthAgo)).build(), LocalDate.now());
        boolean isDueResult = (boolean) isDueMethod.invoke(periodicalSavingsGoalTransferLogic, PeriodicalSavingsGoal.with().duration(createDuration(monthAgo,monthAgo)).build(), monthAgo);

        assertThat(isFinishedResult, is(true));
        assertThat(isDueResult, is(true));

    }

    private PeriodicalSavingsGoal createPeriodicalSavingsGoal(UUID uuid, double number, Set<SavingsRecord> savingsRecordSet, Periodicity periodicity){
        return createPeriodicalSavingsGoal(uuid, number, savingsRecordSet, periodicity, createDuration(number));
    }

    private PeriodicalSavingsGoal createPeriodicalSavingsGoal(UUID uuid, double number, Set<SavingsRecord> savingsRecordSet, Periodicity periodicity, Duration duration){
        return PeriodicalSavingsGoal.with()
                .id(uuid)
                .goal(createMonetaryAmount(number))
                .periodicity(periodicity)
                .targetBankAccountId(uuid)
                .sourceBankAccountId(uuid)
                .savingsRecords(savingsRecordSet)
                .achievementStatus(AchievementStatus.ACHIEVED)
                .name("Periodical Savings Goal")
                .description("description")
                .duration(createDuration(number))
                .recurringAmount(createMonetaryAmount(number))
                .recurringRate(number)
                .build();
    }

    private SavingsRecord createSavingsRecords(UUID uuid, double number){
        return SavingsRecord.with()
                .savingsGoalId(uuid)
                .amount(createMonetaryAmount(number))
                .date(LocalDate.now())
                .achievementStatus(AchievementStatus.FAILED)
                .id(uuid)
                .build();
    }

    private MonetaryAmount createMonetaryAmount(double number){
        MonetaryAmount monetaryAmount = new MonetaryAmount();
        monetaryAmount.setAmount(number);
        return monetaryAmount;
    }
    private Duration createDuration(double number){
        Duration duration = new Duration();
        duration.setEnd(null);
        duration.setStart(LocalDate.now().plusMonths((int)number));
        return duration;
    }
    private Duration createDuration(LocalDate start, LocalDate end){
        Duration duration = new Duration();
        duration.setEnd(end);
        duration.setStart(start);
        return duration;
    }

}
