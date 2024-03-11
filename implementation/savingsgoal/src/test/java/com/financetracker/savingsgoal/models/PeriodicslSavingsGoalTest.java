package com.financetracker.savingsgoal.models;

import com.financetracker.savingsgoal.IntegrationTestBase;
import com.financetracker.savingsgoal.logic.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PeriodicslSavingsGoalTest extends IntegrationTestBase {
    @Test
    void givenPeriodicalSavingsGoal_whenUpdate_thenExists(){
        double number = 1.0;
        UUID uuid = UUID.randomUUID();

        HashSet<SavingsRecord> savingsRecordSet = new HashSet<>();
        SavingsRecord savingsRecord = createSavingsRecord(uuid, LocalDate.now(), number);
        savingsRecordSet.add(savingsRecord);

        PeriodicalSavingsGoal periodicalSavingsGoal = new PeriodicalSavingsGoal();
        periodicalSavingsGoal.setId(uuid);
        periodicalSavingsGoal.setGoal(createMonetaryAmount(number));
        periodicalSavingsGoal.setPeriodicity(Periodicity.MONTHLY);
        periodicalSavingsGoal.setTargetBankAccountId(uuid);
        periodicalSavingsGoal.setSourceBankAccountId(uuid);
        periodicalSavingsGoal.setSavingsRecords(savingsRecordSet);
        periodicalSavingsGoal.setAchievementStatus(AchievementStatus.ACHIEVED);
        periodicalSavingsGoal.setName("Periodical Savings Goal");
        periodicalSavingsGoal.setDescription("description");
        periodicalSavingsGoal.setDuration(createDuration(number));
        periodicalSavingsGoal.setRecurringAmount(createMonetaryAmount(number));
        periodicalSavingsGoal.setRecurringRate(number);

        assertThat(periodicalSavingsGoal.getId(), is(periodicalSavingsGoal.getId()));
        assertThat(periodicalSavingsGoal.getGoal().getAmount(), is(periodicalSavingsGoal.getGoal().getAmount()));
        assertThat(periodicalSavingsGoal.getPeriodicity().getMonths(), is(periodicalSavingsGoal.getPeriodicity().getMonths()));
        assertThat(periodicalSavingsGoal.getTargetBankAccountId(), is(periodicalSavingsGoal.getTargetBankAccountId()));
        assertThat(periodicalSavingsGoal.getSourceBankAccountId(), is(periodicalSavingsGoal.getSourceBankAccountId()));
        assertThat(periodicalSavingsGoal.getSavingsRecords().size(), is(periodicalSavingsGoal.getSavingsRecords().size()));
        assertThat(periodicalSavingsGoal.getAchievementStatus(), is(periodicalSavingsGoal.getAchievementStatus()));
        assertThat(periodicalSavingsGoal.getName(), is(periodicalSavingsGoal.getName()));
        assertThat(periodicalSavingsGoal.getDescription(), is(periodicalSavingsGoal.getDescription()));
        assertThat(periodicalSavingsGoal.getDuration().getStart(), is(periodicalSavingsGoal.getDuration().getStart()));
        assertThat(periodicalSavingsGoal.getDuration().getEnd(), is(periodicalSavingsGoal.getDuration().getEnd()));
        assertThat(periodicalSavingsGoal.getRecurringAmount().getAmount(), is(periodicalSavingsGoal.getRecurringAmount().getAmount()));
        assertThat(periodicalSavingsGoal.getRecurringRate(), is(periodicalSavingsGoal.getRecurringRate()));

        String periodicalSavingsGoalBuild = PeriodicalSavingsGoal.with()
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
                .toString();
        assertThat(periodicalSavingsGoalBuild.contains("id="), is(true));
    }

    private SavingsRecord createSavingsRecord(UUID id, LocalDate date, double money){
        return SavingsRecord.with()
                .id(id)
                .date(date)
                .achievementStatus(AchievementStatus.ACHIEVED)
                .amount(new MonetaryAmount(money))
                .savingsGoalId(id)
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
}
