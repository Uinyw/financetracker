package com.financetracker.savingsgoal.api.mapping;

import com.financetracker.savingsgoal.logic.model.Duration;
import com.financetracker.savingsgoal.logic.model.PeriodicalSavingsGoal;
import com.financetracker.savingsgoal.logic.model.Periodicity;
import com.financetracker.savingsgoal.logic.model.SavingsRecord;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.PeriodicalSavingsGoalDto;
import org.openapitools.model.PeriodicityDto;
import org.openapitools.model.SavingsRecordDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
                .savingsRecords(periodicalSavingsGoal.getSavingsRecords().stream().map(this::savingsRecordModelToDto).toList())
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
                .savingsRecords(periodicalSavingsGoalDto.getSavingsRecords().stream().map(s -> savingsRecordDtoToModel(periodicalSavingsGoalDto.getId(), s)).collect(Collectors.toSet()))
                .build();
    }

    private SavingsRecordDto savingsRecordModelToDto(final SavingsRecord savingsRecord) {
        return SavingsRecordDto.builder()
                .id(savingsRecord.getId())
                .date(savingsRecord.getDate().toString())
                .amount(commonMapper.monetaryAmountModelToDto(savingsRecord.getAmount()))
                .achievementStatus(commonMapper.achievementStatusModelToDto(savingsRecord.getAchievementStatus()))
                .build();
    }

    private SavingsRecord savingsRecordDtoToModel(final UUID savingsGoalId, final SavingsRecordDto savingsRecordDto) {
        return SavingsRecord.with()
                .id(savingsRecordDto.getId())
                .savingsGoalId(savingsGoalId)
                .date(LocalDate.parse(savingsRecordDto.getDate(), getDateTimeFormatter()))
                .amount(commonMapper.monetaryAmountDtoToModel(savingsRecordDto.getAmount()))
                .achievementStatus(commonMapper.achievementStatusDtoToModel(savingsRecordDto.getAchievementStatus()))
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
        String startDate = duration.getStart().format(getDateTimeFormatter());
        String endDate = duration.getEnd() != null ? duration.getEnd().format(getDateTimeFormatter()) : null;
        return startDate + ";" + Optional.ofNullable(endDate).orElse("");
    }

    private Duration stringToDuration(String durationString) {
        Duration duration = new Duration();
        String[] durations = durationString.split(";");

        LocalDate startDate = LocalDate.parse(durations[0], getDateTimeFormatter());
        LocalDate endDate = durations.length == 2 ? LocalDate.parse(durations[1], getDateTimeFormatter()) : null;
        duration.setStart(startDate);
        duration.setEnd(endDate);
        return duration;
    }

    private DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale( Locale.GERMAN );
    }
}
