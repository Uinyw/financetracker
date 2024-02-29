package com.financetracker.savingsgoal.models;

import com.financetracker.savingsgoal.IntegrationTestBase;
import com.financetracker.savingsgoal.logic.model.AchievementStatus;
import com.financetracker.savingsgoal.logic.model.MonetaryAmount;
import com.financetracker.savingsgoal.logic.model.SavingsRecord;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SavingsRecordTest extends IntegrationTestBase {

    @Test
    void givenSavingsRecord_whenUpdate_thenExists(){
        UUID id = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        double money = 20.0;

        SavingsRecord savingsRecord = SavingsRecord.with()
                .id(id)
                .date(date)
                .achievementStatus(AchievementStatus.ACHIEVED)
                .amount(new MonetaryAmount(money))
                .savingsGoalId(id)
                .build();

        assertThat(savingsRecord.getId(), is(id));
        assertThat(savingsRecord.getDate(), is(date));
        assertThat(savingsRecord.getSavingsGoalId(), is(id));
        assertThat(savingsRecord.getAmount().getAmount(), is(money));
        assertThat(savingsRecord.getAchievementStatus(), is(AchievementStatus.ACHIEVED));

        id = UUID.randomUUID();
        date = date.plusDays(1);
        money = 10.0;

        savingsRecord.setSavingsGoalId(id);
        savingsRecord.setId(id);
        savingsRecord.setDate(date);
        savingsRecord.setAmount(new MonetaryAmount(money));
        savingsRecord.setAchievementStatus(AchievementStatus.FAILED);

        assertThat(savingsRecord.getId(), is(id));
        assertThat(savingsRecord.getDate(), is(date));
        assertThat(savingsRecord.getSavingsGoalId(), is(id));
        assertThat(savingsRecord.getAmount().getAmount(), is(10.0));
        assertThat(savingsRecord.getAchievementStatus(), is(AchievementStatus.FAILED));
    }
}
