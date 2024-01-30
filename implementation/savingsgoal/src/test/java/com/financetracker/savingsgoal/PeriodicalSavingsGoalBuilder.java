package com.financetracker.savingsgoal;

import org.openapitools.model.AchievementStatus;
import org.openapitools.model.MonetaryAmount;
import org.openapitools.model.PeriodicalSavingsGoalDTO;
import org.openapitools.model.Periodicity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PeriodicalSavingsGoalBuilder {
    public static PeriodicalSavingsGoalDTO buildWithDefaults(){
        String duration = "";
        Periodicity periodicity = Periodicity.MONTHLY;
        MonetaryAmount recurringAmount = new MonetaryAmount();
        MonetaryAmount goal = new MonetaryAmount();
        List<UUID> transactionRecord = new ArrayList<>();
        return build("test",UUID.randomUUID(),"testDescription",UUID.randomUUID(),AchievementStatus.IN_PROGRESS, PeriodicalSavingsGoalDTO.TypeEnum.AMOUNT_BASED,periodicity, transactionRecord, goal, duration, recurringAmount);
    }

    public static PeriodicalSavingsGoalDTO build(String name, UUID id, String description, UUID bankAccountId, AchievementStatus achievementStatus, PeriodicalSavingsGoalDTO.TypeEnum type, Periodicity periodicity, List<UUID> transactionRecord, MonetaryAmount goal, String duration, MonetaryAmount recurringAmount){
        PeriodicalSavingsGoalDTO periodicalSavingsGoalDTO = new PeriodicalSavingsGoalDTO();
        periodicalSavingsGoalDTO.setPeriodicity(periodicity);
        periodicalSavingsGoalDTO.setOneTimeTransactionRecord(transactionRecord);
        periodicalSavingsGoalDTO.setGoal(goal);
        periodicalSavingsGoalDTO.setDuration(duration);
        periodicalSavingsGoalDTO.setRecurringAmount(recurringAmount);
        periodicalSavingsGoalDTO.setName(name);
        periodicalSavingsGoalDTO.setId(id);
        periodicalSavingsGoalDTO.setDescription(description);
        periodicalSavingsGoalDTO.setType(type);
        periodicalSavingsGoalDTO.setBankAccountID(bankAccountId);
        periodicalSavingsGoalDTO.setAchievementStatus(achievementStatus);
        return periodicalSavingsGoalDTO;
    }
}
