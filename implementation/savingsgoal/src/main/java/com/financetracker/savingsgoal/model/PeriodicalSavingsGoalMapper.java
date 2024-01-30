package com.financetracker.savingsgoal.model;

import com.financetracker.savingsgoal.Duration;
import com.financetracker.savingsgoal.MonetaryAmount;
import com.financetracker.savingsgoal.PeriodicalSavingsGoal;
import org.openapitools.model.AchievementStatus;
import org.openapitools.model.PeriodicalSavingsGoalDTO;
import org.openapitools.model.Periodicity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Component
public class PeriodicalSavingsGoalMapper {
    public PeriodicalSavingsGoalDTO periodicalSavingsGoalEntityToDTO(PeriodicalSavingsGoal periodicalSavingsGoal){
        String duration = SavingsGoalMapper.durationToString(periodicalSavingsGoal.getDuration());

        return createPeriodicalSavingsGoalDTO(periodicalSavingsGoal.getId(),
                periodicalSavingsGoal.getName(),
                periodicalSavingsGoal.getDescription(),
                periodicalSavingsGoal.getAchievementStatus(),
                periodicalSavingsGoal.getBankAccountId(),
                periodicalSavingsGoal.getGoal(),
                periodicalSavingsGoal.getRecurringRate().getAmount(),
                periodicalSavingsGoal.getRecurringAmount(),
                duration,
                periodicalSavingsGoal.getPeriodicity());
    }
    public PeriodicalSavingsGoal periodicalSavingsGoalDTOtoEntity(PeriodicalSavingsGoalDTO periodicalSavingsGoalDTO){
        org.openapitools.model.MonetaryAmount reoccuringRate = new org.openapitools.model.MonetaryAmount();
        reoccuringRate.setAmount(periodicalSavingsGoalDTO.getRecurringRate());

        Duration duration = SavingsGoalMapper.stringToDuration(periodicalSavingsGoalDTO.getDuration());

        return createPeriodicalSavingsGoal(periodicalSavingsGoalDTO.getBankAccountID(),
                reoccuringRate,
                duration,
                periodicalSavingsGoalDTO.getOneTimeTransactionRecord(),
                SavingsGoalMapper.monetaryAmountDTOtoModel(periodicalSavingsGoalDTO.getRecurringAmount()),
                SavingsGoalMapper.monetaryAmountDTOtoModel(periodicalSavingsGoalDTO.getGoal()));
    }




    private PeriodicalSavingsGoalDTO createPeriodicalSavingsGoalDTO(UUID id, String name, String description, AchievementStatus achievementStatus, UUID bankAccountId, MonetaryAmount goal, double reoccuringRate, MonetaryAmount reoccuringAmount, String duration, Periodicity periodicity){
        PeriodicalSavingsGoalDTO periodicalSavingsGoalDTO = new PeriodicalSavingsGoalDTO();

        periodicalSavingsGoalDTO.setId(id);
        periodicalSavingsGoalDTO.setName(name);
        periodicalSavingsGoalDTO.setDescription(description);
        periodicalSavingsGoalDTO.setAchievementStatus(achievementStatus);
        periodicalSavingsGoalDTO.setBankAccountID(bankAccountId);
        periodicalSavingsGoalDTO.setGoal(SavingsGoalMapper.monetaryAmountModeltoDTO(goal));
        periodicalSavingsGoalDTO.setRecurringRate(reoccuringRate);
        periodicalSavingsGoalDTO.setRecurringAmount(SavingsGoalMapper.monetaryAmountModeltoDTO(reoccuringAmount));
        periodicalSavingsGoalDTO.setDuration(duration);
        periodicalSavingsGoalDTO.setPeriodicity(periodicity);
        return periodicalSavingsGoalDTO;
    }

    private PeriodicalSavingsGoal createPeriodicalSavingsGoal(UUID bankAccountId, org.openapitools.model.MonetaryAmount reoccuringRate, Duration duration, List<UUID> transactionIds, MonetaryAmount reoccuringAmount, MonetaryAmount goal){
        return PeriodicalSavingsGoal.with()
                .id(bankAccountId)
                .recurringRate(SavingsGoalMapper.monetaryAmountDTOtoModel(reoccuringRate))
                .duration(duration)
                .transactionIds(transactionIds)
                .recurringAmount(reoccuringAmount)
                .goal(goal)
                .build();
    }

    public LocalDate getTimeFromString(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        formatter = formatter.withLocale( Locale.GERMAN );

        return  LocalDate.parse(date, formatter);
    }
}
