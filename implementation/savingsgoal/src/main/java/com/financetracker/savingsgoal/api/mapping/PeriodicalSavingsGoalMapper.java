package com.financetracker.savingsgoal.api.mapping;

import com.financetracker.savingsgoal.logic.model.Duration;
import com.financetracker.savingsgoal.logic.model.PeriodicalSavingsGoal;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.MonetaryAmount;
import org.openapitools.model.PeriodicalSavingsGoalDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RequiredArgsConstructor
@Component
public class PeriodicalSavingsGoalMapper {

    public PeriodicalSavingsGoalDTO periodicalSavingsGoalEntityToDTO(PeriodicalSavingsGoal periodicalSavingsGoal) {
        return PeriodicalSavingsGoalDTO.builder()
                .id(periodicalSavingsGoal.getId())
                .name(periodicalSavingsGoal.getName())
                .description(periodicalSavingsGoal.getDescription())
                .achievementStatus(periodicalSavingsGoal.getAchievementStatus())
                .sourceBankAccountID(periodicalSavingsGoal.getSourceBankAccountId())
                .targetBankAccountID(periodicalSavingsGoal.getTargetBankAccountId())
                .goal(MonetaryAmount.builder().amount(periodicalSavingsGoal.getGoal().getAmount()).build())
                .recurringRate(periodicalSavingsGoal.getRecurringRate())
                .recurringAmount(MonetaryAmount.builder().amount(periodicalSavingsGoal.getRecurringAmount().getAmount()).build())
                .duration(durationToString(periodicalSavingsGoal.getDuration()))
                .periodicity(periodicalSavingsGoal.getPeriodicity())
                .build();
    }

    public PeriodicalSavingsGoal periodicalSavingsGoalDTOtoEntity(PeriodicalSavingsGoalDTO periodicalSavingsGoalDTO) {
        return PeriodicalSavingsGoal.with()
                .id(periodicalSavingsGoalDTO.getId())
                .name(periodicalSavingsGoalDTO.getName())
                .description(periodicalSavingsGoalDTO.getDescription())
                .achievementStatus(periodicalSavingsGoalDTO.getAchievementStatus())
                .sourceBankAccountId(periodicalSavingsGoalDTO.getSourceBankAccountID())
                .targetBankAccountId(periodicalSavingsGoalDTO.getTargetBankAccountID())
                .goal(new com.financetracker.savingsgoal.logic.model.MonetaryAmount(periodicalSavingsGoalDTO.getGoal().getAmount()))
                .recurringRate(periodicalSavingsGoalDTO.getRecurringRate())
                .recurringAmount(new com.financetracker.savingsgoal.logic.model.MonetaryAmount(periodicalSavingsGoalDTO.getRecurringAmount().getAmount()))
                .duration(stringToDuration(periodicalSavingsGoalDTO.getDuration()))
                .transactionIds(periodicalSavingsGoalDTO.getOneTimeTransactionRecord())
                .build();
    }

    public LocalDate getTimeFromString(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        formatter = formatter.withLocale( Locale.GERMAN );

        return  LocalDate.parse(date, formatter);
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
        //TODO fehleranällig
        System.out.println("durations");
        System.out.println(durations[0]);
        LocalDate startDate = LocalDate.parse(durations[0], formatter);
        LocalDate endDate = LocalDate.parse(durations[1], formatter);
        duration.setStart(startDate);
        duration.setEnd(endDate);
        return duration;
    }
}
