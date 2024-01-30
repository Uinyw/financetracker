package com.financetracker.savingsgoal.model;

import com.financetracker.savingsgoal.*;
import org.openapitools.model.AchievementStatus;
import org.openapitools.model.PeriodicalSavingsGoalDTO;
import org.openapitools.model.Periodicity;
import org.openapitools.model.RuleBasedSavingsGoalDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class SavingsGoalMapper {



    public static MonetaryAmount monetaryAmountDTOtoModel(org.openapitools.model.MonetaryAmount monetaryAmount){
        MonetaryAmount monetaryAmountModel = new MonetaryAmount();
        monetaryAmountModel.setAmount(monetaryAmount.getAmount());
        return monetaryAmountModel;
    }

    public static org.openapitools.model.MonetaryAmount monetaryAmountModeltoDTO(MonetaryAmount monetaryAmount){
        org.openapitools.model.MonetaryAmount monetaryAmountModel = new org.openapitools.model.MonetaryAmount();
        monetaryAmountModel.setAmount(monetaryAmount.getAmount());
        return monetaryAmountModel;
    }

    public static String durationToString(Duration duration){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        formatter = formatter.withLocale( Locale.GERMAN );
        String startDate = duration.getStart().format(formatter);
        String endDate = duration.getEnd().format(formatter);
        String result = startDate + ";" + endDate;
        return result;
    }
    public static Duration stringToDuration(String durationString){
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

    public static LocalDate getTimeFromString(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        formatter = formatter.withLocale( Locale.GERMAN );

        return  LocalDate.parse(date, formatter);
    }
}
