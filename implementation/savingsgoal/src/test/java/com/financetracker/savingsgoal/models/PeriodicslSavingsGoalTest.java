package com.financetracker.savingsgoal.models;

import com.financetracker.savingsgoal.IntegrationTestBase;
import com.financetracker.savingsgoal.logic.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.UUID;

public class PeriodicslSavingsGoalTest extends IntegrationTestBase {
    @Test
    void givenPeriodicalSavingsGoal_whenUpdate_thenExists(){
        PeriodicalSavingsGoal periodicalSavingsGoal = createPeriodicalSavingsGoal(UUID.randomUUID(), 1.0);
        double amount = 1.0;
        UUID uuid = UUID.randomUUID();


    }


    private PeriodicalSavingsGoal createPeriodicalSavingsGoal(UUID uuid, double number){
        return PeriodicalSavingsGoal.with()
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
