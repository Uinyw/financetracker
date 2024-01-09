package com.financetracker.savingsgoal.model;

import com.financetracker.savingsgoal.*;
import jakarta.persistence.*;
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

    public PeriodicalSavingsGoalDTO periodicalSavingsGoalEntityToDTO(PeriodicalSavingsGoal periodicalSavingsGoal){
        MonetaryAmount monetaryAmount = new MonetaryAmount();
        monetaryAmount.setAmount(periodicalSavingsGoal.getGoal().getAmount());

        MonetaryAmount reoccuringAmount = new MonetaryAmount();
        reoccuringAmount.setAmount(periodicalSavingsGoal.getRecurringAmount().getAmount());

        String duration = durationToString(periodicalSavingsGoal.getDuration());

        PeriodicalSavingsGoalDTO periodicalSavingsGoalDTO = createPeriodicalSavingsGoalDTO(periodicalSavingsGoal.getId(),
                periodicalSavingsGoal.getName(),
                periodicalSavingsGoal.getDescription(),
                periodicalSavingsGoal.getAchievementStatus(),
                periodicalSavingsGoal.getBankAccountId(),
                monetaryAmount,periodicalSavingsGoal.getRecurringRate().getAmount(),
                reoccuringAmount,
                duration,
                periodicalSavingsGoal.getPeriodicity());
        return periodicalSavingsGoalDTO;
    }
    public PeriodicalSavingsGoal periodicalSavingsGoalDTOtoEntity(PeriodicalSavingsGoalDTO periodicalSavingsGoalDTO){
        //TODO more work required (savings records missmatch)

        org.openapitools.model.MonetaryAmount reoccuringRate = new org.openapitools.model.MonetaryAmount();
        reoccuringRate.setAmount(periodicalSavingsGoalDTO.getRecurringRate());

        Duration duration = stringToDuration(periodicalSavingsGoalDTO.getDuration());

        PeriodicalSavingsGoal periodicalSavingsGoal = createPeriodicalSavingsGoal(periodicalSavingsGoalDTO.getBankAccountID(),
                reoccuringRate,
                duration,null ,monetaryAmountDTOtoModel(periodicalSavingsGoalDTO.getRecurringAmount()),monetaryAmountDTOtoModel(periodicalSavingsGoalDTO.getGoal()));
        return periodicalSavingsGoal;
    }

    public RuleBasedSavingsGoalDTO ruleBasedSavingsGoalEntityToDTO(RuleBasedSavingsGoal ruleBasedSavingsGoal){

        return null;
    }
    public RuleBasedSavingsGoal ruleBasedSavingsGoalDTOtoEntity(RuleBasedSavingsGoalDTO ruleBasedSavingsGoalDTO){
        return null;
    }


    public static PeriodicalSavingsGoalDTO createPeriodicalSavingsGoalDTO(UUID id, String name, String description, AchievementStatus achievementStatus, UUID bankAccountId, MonetaryAmount goal, double reoccuringRate, MonetaryAmount reoccuringAmount, String duration, Periodicity periodicity){
        PeriodicalSavingsGoalDTO periodicalSavingsGoalDTO = new PeriodicalSavingsGoalDTO();

        periodicalSavingsGoalDTO.setId(id);
        periodicalSavingsGoalDTO.setName(name);
        periodicalSavingsGoalDTO.setDescription(description);
        periodicalSavingsGoalDTO.setAchievementStatus(achievementStatus);
        periodicalSavingsGoalDTO.setBankAccountID(bankAccountId);
        periodicalSavingsGoalDTO.setGoal(monetaryAmountModeltoDTO(goal));
        periodicalSavingsGoalDTO.setRecurringRate(reoccuringRate);
        periodicalSavingsGoalDTO.setRecurringAmount(monetaryAmountModeltoDTO(reoccuringAmount));
        periodicalSavingsGoalDTO.setDuration(duration);
        periodicalSavingsGoalDTO.setPeriodicity(periodicity);
        return periodicalSavingsGoalDTO;
    }

    public static PeriodicalSavingsGoal createPeriodicalSavingsGoal(UUID bankAccountId, org.openapitools.model.MonetaryAmount reoccuringRate, Duration duration, List<SavingsRecord> records, MonetaryAmount reoccuringAmount, MonetaryAmount goal){
        PeriodicalSavingsGoal periodicalSavingsGoal = new PeriodicalSavingsGoal();
        periodicalSavingsGoal.setBankAccountId(bankAccountId);
        periodicalSavingsGoal.setRecurringRate(reoccuringRate);
        periodicalSavingsGoal.setDuration(duration);
        periodicalSavingsGoal.setRecords(records);
        periodicalSavingsGoal.setRecurringAmount(monetaryAmountModeltoDTO(reoccuringAmount));
        periodicalSavingsGoal.setGoal(monetaryAmountModeltoDTO(goal));
        return periodicalSavingsGoal;
    }

    private static MonetaryAmount monetaryAmountDTOtoModel(org.openapitools.model.MonetaryAmount monetaryAmount){
        MonetaryAmount monetaryAmountModel = new MonetaryAmount();
        monetaryAmountModel.setAmount(monetaryAmount.getAmount());
        return monetaryAmountModel;
    }

    private static org.openapitools.model.MonetaryAmount monetaryAmountModeltoDTO(MonetaryAmount monetaryAmount){
        org.openapitools.model.MonetaryAmount monetaryAmountModel = new org.openapitools.model.MonetaryAmount();
        monetaryAmountModel.setAmount(monetaryAmount.getAmount());
        return monetaryAmountModel;
    }

    private static String durationToString(Duration duration){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        formatter = formatter.withLocale( Locale.GERMAN );
        String startDate = duration.getStart().format(formatter);
        String endDate = duration.getEnd().format(formatter);
        String result = startDate + ";" + endDate;
        return result;
    }
    private static Duration stringToDuration(String durationString){
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
