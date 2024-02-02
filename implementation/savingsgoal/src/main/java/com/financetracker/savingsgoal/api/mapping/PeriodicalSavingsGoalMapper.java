package com.financetracker.savingsgoal.api.mapping;

import com.financetracker.savingsgoal.logic.model.Duration;
import com.financetracker.savingsgoal.logic.model.PeriodicalSavingsGoal;
import com.financetracker.savingsgoal.logic.model.Periodicity;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.PeriodicalSavingsGoalDto;
import org.openapitools.model.PeriodicityDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RequiredArgsConstructor
@Component
public class PeriodicalSavingsGoalMapper {

    private final CommonMapper commonMapper;

    public PeriodicalSavingsGoalDto periodicalSavingsGoalEntityToDto(PeriodicalSavingsGoal periodicalSavingsGoal) {
        return PeriodicalSavingsGoalDto.builder()
                .id(periodicalSavingsGoal.getId())
                .name(periodicalSavingsGoal.getName())
                .description(periodicalSavingsGoal.getDescription())
                .achievementStatus(commonMapper.achievementStatusModelToDto(periodicalSavingsGoal.getAchievementStatus()))
                .sourceBankAccountID(periodicalSavingsGoal.getSourceBankAccountId())
                .targetBankAccountID(periodicalSavingsGoal.getTargetBankAccountId())
                .goal(commonMapper.monetaryAmountModelToDto(periodicalSavingsGoal.getGoal()))
                .recurringRate(periodicalSavingsGoal.getRecurringRate())
                .recurringAmount(commonMapper.monetaryAmountModelToDto(periodicalSavingsGoal.getRecurringAmount()))
                .duration(durationToString(periodicalSavingsGoal.getDuration()))
                .periodicity(periodicityModelToDto(periodicalSavingsGoal.getPeriodicity()))
                .build();
    }

    public PeriodicalSavingsGoal periodicalSavingsGoalDtoToEntity(PeriodicalSavingsGoalDto periodicalSavingsGoalDto) {
        return PeriodicalSavingsGoal.with()
                .id(periodicalSavingsGoalDto.getId())
                .name(periodicalSavingsGoalDto.getName())
                .description(periodicalSavingsGoalDto.getDescription())
                .achievementStatus(commonMapper.achievementStatusDtoToModel(periodicalSavingsGoalDto.getAchievementStatus()))
                .sourceBankAccountId(periodicalSavingsGoalDto.getSourceBankAccountID())
                .targetBankAccountId(periodicalSavingsGoalDto.getTargetBankAccountID())
                .goal(new com.financetracker.savingsgoal.logic.model.MonetaryAmount(periodicalSavingsGoalDto.getGoal().getAmount()))
                .recurringRate(periodicalSavingsGoalDto.getRecurringRate())
                .recurringAmount(new com.financetracker.savingsgoal.logic.model.MonetaryAmount(periodicalSavingsGoalDto.getRecurringAmount().getAmount()))
                .duration(stringToDuration(periodicalSavingsGoalDto.getDuration()))
                .periodicity(periodicityDtoToModel(periodicalSavingsGoalDto.getPeriodicity()))
                .build();
    }

    private PeriodicityDto periodicityModelToDto(final Periodicity periodicity) {
        return switch (periodicity) {
            case MONTHLY -> PeriodicityDto.MONTHLY;
            case QUARTERLY -> PeriodicityDto.QUARTERLY;
            case HALF_YEARLY -> PeriodicityDto.HALF_YEARLY;
            case YEARLY -> PeriodicityDto.YEARLY;
        };
    }

    private Periodicity periodicityDtoToModel(final PeriodicityDto periodicityDto) {
        return switch (periodicityDto) {
            case MONTHLY -> Periodicity.MONTHLY;
            case QUARTERLY -> Periodicity.QUARTERLY;
            case HALF_YEARLY -> Periodicity.HALF_YEARLY;
            case YEARLY -> Periodicity.YEARLY;
        };
    }

    private String durationToString(Duration duration) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        formatter = formatter.withLocale( Locale.GERMAN );
        String startDate = duration.getStart().format(formatter);
        String endDate = duration.getEnd().format(formatter);
        String result = startDate + ";" + endDate;
        return result;
    }


    private Duration stringToDuration(String durationString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        formatter = formatter.withLocale( Locale.GERMAN );

        Duration duration = new Duration();
        String[] durations = durationString.split(";");

        LocalDate startDate = LocalDate.parse(durations[0], formatter);
        LocalDate endDate = LocalDate.parse(durations[1], formatter);
        duration.setStart(startDate);
        duration.setEnd(endDate);
        return duration;
    }
}
