package com.financetracker.savingsgoal.api.mapper;

import com.financetracker.savingsgoal.IntegrationTestBase;
import com.financetracker.savingsgoal.api.mapping.PeriodicalSavingsGoalMapper;
import com.financetracker.savingsgoal.logic.model.*;
import org.junit.jupiter.api.Test;
import org.openapitools.model.PeriodicalSavingsGoalDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PeriodicalSavingsGoalMapperTest extends IntegrationTestBase {
    @Autowired
    private PeriodicalSavingsGoalMapper periodicalSavingsGoalMapper;

    @Test
    void testPeriodicalSavingsGoalEntity_whenCreate_existsPeriodicalSavingsGoalDto(){
        UUID uuid = UUID.randomUUID();
        double number = 2.0;
        Set<SavingsRecord> savingsRecordSet = new HashSet<>();
        SavingsRecord savingsRecord = createSavingsRecords(uuid, number);
        savingsRecordSet.add(savingsRecord);

        PeriodicalSavingsGoal periodicalSavingsGoal = createPeriodicalSavingsGoal(uuid, number, savingsRecordSet, Periodicity.YEARLY);

        PeriodicalSavingsGoalDto periodicalSavingsGoalDto = periodicalSavingsGoalMapper.periodicalSavingsGoalEntityToDto(periodicalSavingsGoal);
        PeriodicalSavingsGoal newPeriodicalSavingsGoal = periodicalSavingsGoalMapper.periodicalSavingsGoalDtoToEntity(periodicalSavingsGoalDto);

        assertThat(newPeriodicalSavingsGoal.getId(),is(periodicalSavingsGoal.getId()));
        assertThat(newPeriodicalSavingsGoal.getGoal().getAmount(),is(periodicalSavingsGoal.getGoal().getAmount()));
        assertThat(newPeriodicalSavingsGoal.getPeriodicity().getMonths(),is(periodicalSavingsGoal.getPeriodicity().getMonths()));
        assertThat(newPeriodicalSavingsGoal.getTargetBankAccountId(),is(periodicalSavingsGoal.getTargetBankAccountId()));
        assertThat(newPeriodicalSavingsGoal.getSourceBankAccountId(),is(periodicalSavingsGoal.getSourceBankAccountId()));
        assertThat(newPeriodicalSavingsGoal.getSavingsRecords().size(),is(periodicalSavingsGoal.getSavingsRecords().size()));
        assertThat(newPeriodicalSavingsGoal.getAchievementStatus().name(),is(periodicalSavingsGoal.getAchievementStatus().name()));
        assertThat(newPeriodicalSavingsGoal.getName(),is(periodicalSavingsGoal.getName()));
        assertThat(newPeriodicalSavingsGoal.getDescription(),is(periodicalSavingsGoal.getDescription()));
        assertThat(newPeriodicalSavingsGoal.getDuration().getStart(),is(periodicalSavingsGoal.getDuration().getStart()));
        assertThat(newPeriodicalSavingsGoal.getDuration().getEnd(),is(periodicalSavingsGoal.getDuration().getEnd()));
        assertThat(newPeriodicalSavingsGoal.getRecurringAmount().getAmount(),is(periodicalSavingsGoal.getRecurringAmount().getAmount()));
        assertThat(newPeriodicalSavingsGoal.getRecurringRate(),is(periodicalSavingsGoal.getRecurringRate()));

        Set<SavingsRecord> sset = newPeriodicalSavingsGoal.getSavingsRecords();
        SavingsRecord newSavingsRecord = sset.stream().findFirst().get();

        assertThat(newSavingsRecord.getId(),is(savingsRecord.getId()));
        assertThat(newSavingsRecord.getAmount().getAmount(),is(savingsRecord.getAmount().getAmount()));
        assertThat(newSavingsRecord.getDate(),is(savingsRecord.getDate()));
        assertThat(newSavingsRecord.getAchievementStatus(),is(savingsRecord.getAchievementStatus()));


        periodicalSavingsGoal = createPeriodicalSavingsGoal(uuid, number, savingsRecordSet, Periodicity.QUARTERLY);
        periodicalSavingsGoalDto = periodicalSavingsGoalMapper.periodicalSavingsGoalEntityToDto(periodicalSavingsGoal);
        newPeriodicalSavingsGoal = periodicalSavingsGoalMapper.periodicalSavingsGoalDtoToEntity(periodicalSavingsGoalDto);

        assertThat(newPeriodicalSavingsGoal.getId(),is(periodicalSavingsGoal.getId()));
        assertThat(newPeriodicalSavingsGoal.getGoal().getAmount(),is(periodicalSavingsGoal.getGoal().getAmount()));
        assertThat(newPeriodicalSavingsGoal.getPeriodicity().getMonths(),is(periodicalSavingsGoal.getPeriodicity().getMonths()));
        assertThat(newPeriodicalSavingsGoal.getTargetBankAccountId(),is(periodicalSavingsGoal.getTargetBankAccountId()));
        assertThat(newPeriodicalSavingsGoal.getSourceBankAccountId(),is(periodicalSavingsGoal.getSourceBankAccountId()));
        assertThat(newPeriodicalSavingsGoal.getSavingsRecords().size(),is(periodicalSavingsGoal.getSavingsRecords().size()));
        assertThat(newPeriodicalSavingsGoal.getAchievementStatus().name(),is(periodicalSavingsGoal.getAchievementStatus().name()));
        assertThat(newPeriodicalSavingsGoal.getName(),is(periodicalSavingsGoal.getName()));
        assertThat(newPeriodicalSavingsGoal.getDescription(),is(periodicalSavingsGoal.getDescription()));
        assertThat(newPeriodicalSavingsGoal.getDuration().getStart(),is(periodicalSavingsGoal.getDuration().getStart()));
        assertThat(newPeriodicalSavingsGoal.getDuration().getEnd(),is(periodicalSavingsGoal.getDuration().getEnd()));
        assertThat(newPeriodicalSavingsGoal.getRecurringAmount().getAmount(),is(periodicalSavingsGoal.getRecurringAmount().getAmount()));
        assertThat(newPeriodicalSavingsGoal.getRecurringRate(),is(periodicalSavingsGoal.getRecurringRate()));

        periodicalSavingsGoal = createPeriodicalSavingsGoal(uuid, number, savingsRecordSet, Periodicity.HALF_YEARLY);
        periodicalSavingsGoalDto = periodicalSavingsGoalMapper.periodicalSavingsGoalEntityToDto(periodicalSavingsGoal);
        newPeriodicalSavingsGoal = periodicalSavingsGoalMapper.periodicalSavingsGoalDtoToEntity(periodicalSavingsGoalDto);

        assertThat(newPeriodicalSavingsGoal.getId(),is(periodicalSavingsGoal.getId()));
        assertThat(newPeriodicalSavingsGoal.getGoal().getAmount(),is(periodicalSavingsGoal.getGoal().getAmount()));
        assertThat(newPeriodicalSavingsGoal.getPeriodicity().getMonths(),is(periodicalSavingsGoal.getPeriodicity().getMonths()));
        assertThat(newPeriodicalSavingsGoal.getTargetBankAccountId(),is(periodicalSavingsGoal.getTargetBankAccountId()));
        assertThat(newPeriodicalSavingsGoal.getSourceBankAccountId(),is(periodicalSavingsGoal.getSourceBankAccountId()));
        assertThat(newPeriodicalSavingsGoal.getSavingsRecords().size(),is(periodicalSavingsGoal.getSavingsRecords().size()));
        assertThat(newPeriodicalSavingsGoal.getAchievementStatus().name(),is(periodicalSavingsGoal.getAchievementStatus().name()));
        assertThat(newPeriodicalSavingsGoal.getName(),is(periodicalSavingsGoal.getName()));
        assertThat(newPeriodicalSavingsGoal.getDescription(),is(periodicalSavingsGoal.getDescription()));
        assertThat(newPeriodicalSavingsGoal.getDuration().getStart(),is(periodicalSavingsGoal.getDuration().getStart()));
        assertThat(newPeriodicalSavingsGoal.getDuration().getEnd(),is(periodicalSavingsGoal.getDuration().getEnd()));
        assertThat(newPeriodicalSavingsGoal.getRecurringAmount().getAmount(),is(periodicalSavingsGoal.getRecurringAmount().getAmount()));
        assertThat(newPeriodicalSavingsGoal.getRecurringRate(),is(periodicalSavingsGoal.getRecurringRate()));
    }

    @Test
    void stringToDuration_whenMapped_isEqual() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Duration duration = new Duration();
        duration.setStart(LocalDate.now());
        duration.setEnd(null);

        Class<?> clazz = PeriodicalSavingsGoalMapper.class;
        Method durationToStringMethod = clazz.getDeclaredMethod("durationToString", Duration.class);
        durationToStringMethod.setAccessible(true);
        Method stringToDurationMethod = clazz.getDeclaredMethod("stringToDuration", String.class);
        stringToDurationMethod.setAccessible(true);

        String resultString = (String) durationToStringMethod.invoke(periodicalSavingsGoalMapper, duration);
        Duration newDuration = (Duration) stringToDurationMethod.invoke(periodicalSavingsGoalMapper, resultString);


        assertThat(newDuration.getEnd(),is(duration.getEnd()));
        assertThat(newDuration.getStart(),is(duration.getStart()));

        duration.setEnd(null);
        resultString = (String) durationToStringMethod.invoke(periodicalSavingsGoalMapper, duration);
        newDuration = (Duration) stringToDurationMethod.invoke(periodicalSavingsGoalMapper, resultString);

        assertThat(newDuration.getEnd(),is(duration.getEnd()));
        assertThat(newDuration.getStart(),is(duration.getStart()));
    }


    private PeriodicalSavingsGoal createPeriodicalSavingsGoal(UUID uuid, double number, Set<SavingsRecord> savingsRecordSet, Periodicity periodicity){
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

}
