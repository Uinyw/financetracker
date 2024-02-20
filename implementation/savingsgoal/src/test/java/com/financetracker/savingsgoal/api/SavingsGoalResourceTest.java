package com.financetracker.savingsgoal.api;

import com.financetracker.savingsgoal.IntegrationTestBase;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.openapitools.model.AchievementStatusDto;
import org.openapitools.model.MonetaryAmountDto;
import org.openapitools.model.PeriodicalSavingsGoalDto;
import org.openapitools.model.PeriodicityDto;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;

class SavingsGoalResourceTest extends IntegrationTestBase {

    @Test
    void givenPeriodicalSavingsGoalDto_whenCreateSavingsGoal_thenSavingsGoalExists() {
        final var periodicalSavingsGoal = PeriodicalSavingsGoalDto.builder()
                .id(UUID.randomUUID())
                .name("Savings")
                .description("Monthly Savings")
                .periodicity(PeriodicityDto.MONTHLY)
                .achievementStatus(AchievementStatusDto.IN_PROGRESS)
                .sourceBankAccountId(UUID.randomUUID())
                .targetBankAccountId(UUID.randomUUID())
                .goal(MonetaryAmountDto.builder().amount(500.0).build())
                .recurringAmount(MonetaryAmountDto.builder().amount(100.0).build())
                .duration(LocalDate.now().toString())
                .savingsRecords(Collections.emptyList())
                .build();

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/savings-goals/periodical")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(0));

        given().port(port)
                .contentType(ContentType.JSON)
                .body(periodicalSavingsGoal)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/savings-goals/periodical")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/savings-goals/periodical")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(1))
                .and().body("[0].id", is(periodicalSavingsGoal.getId().toString()))
                .and().body("[0].name", is(periodicalSavingsGoal.getName()))
                .and().body("[0].description", is(periodicalSavingsGoal.getDescription()))
                .and().body("[0].periodicity", is(periodicalSavingsGoal.getPeriodicity().toString()))
                .and().body("[0].sourceBankAccountId", is(periodicalSavingsGoal.getSourceBankAccountId().toString()))
                .and().body("[0].targetBankAccountId", is(periodicalSavingsGoal.getTargetBankAccountId().toString()))
                .and().body("[0].goal.amount", is(500F))
                .and().body("[0].recurringAmount.amount", is(100F))
                .and().body("[0].recurringRate", is(nullValue()))
                .and().body("[0].duration", is(periodicalSavingsGoal.getDuration() + ";"))
                .and().body("[0].savingsRecords.size()", is(0));

    }
}
