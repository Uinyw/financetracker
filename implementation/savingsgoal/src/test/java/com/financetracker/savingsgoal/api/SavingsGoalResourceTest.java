package com.financetracker.savingsgoal.api;

import com.financetracker.savingsgoal.IntegrationTestBase;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.openapitools.model.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;

class SavingsGoalResourceTest extends IntegrationTestBase {

    @Test
    void givenPeriodicalSavingsGoalDto_whenCreateSavingsGoal_thenSavingsGoalExists() {
        final UUID uuid = UUID.randomUUID();
        final var periodicalSavingsGoal = PeriodicalSavingsGoalDto.builder()
                .id(uuid)
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

    @Test
    void givenPeriodicalSavingsGoalDto_whenDeleted_thenNotExists(){
        final UUID uuid = UUID.randomUUID();
        final var periodicalSavingsGoal = PeriodicalSavingsGoalDto.builder()
                .id(uuid)
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
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/savings-goals/periodical/" + periodicalSavingsGoal.getId().toString())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(periodicalSavingsGoal)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/savings-goals/periodical")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/savings-goals/periodical/" + periodicalSavingsGoal.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .body("id", Matchers.is(periodicalSavingsGoal.getId().toString()));

        given().port(port)
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/savings-goals/periodical/" + periodicalSavingsGoal.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/savings-goals/periodical")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(0));
    }

    @Test
    void givenPeriodicalSavingsGoalDto_whenPatchedSavingsGoal_thenSavingsGoalUpdate(){
        final UUID uuid = UUID.randomUUID();
        final var periodicalSavingsGoal = PeriodicalSavingsGoalDto.builder()
                .id(uuid)
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
                .contentType(ContentType.JSON)
                .body(periodicalSavingsGoal)
                .patch(LOCAL_BASE_URL_WITHOUT_PORT + "/savings-goals/periodical/" + periodicalSavingsGoal.getId().toString())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(periodicalSavingsGoal)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/savings-goals/periodical")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(periodicalSavingsGoal)
                .patch(LOCAL_BASE_URL_WITHOUT_PORT + "/savings-goals/periodical/" + periodicalSavingsGoal.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void givenRuleBasedSavingsGoalDto_whenDeleted_thenNotExists(){
        final UUID uuid = UUID.randomUUID();
        final var ruleBasedSavingsGoal = RuleBasedSavingsGoalDto.builder()
                .id(uuid)
                .name("Savings")
                .description("Monthly Savings")
                .achievementStatus(AchievementStatusDto.ACHIEVED)
                .matchingType(MatchingTypeDto.ALL)
                .rules(Collections.emptyList())
                .build();

        given().port(port)
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/savings-goals/rule-based/" + ruleBasedSavingsGoal.getId().toString())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/savings-goals/rule-based")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(0));

        given().port(port)
                .contentType(ContentType.JSON)
                .body(ruleBasedSavingsGoal)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/savings-goals/rule-based")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/savings-goals/rule-based/" + ruleBasedSavingsGoal.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .body("id", Matchers.is(ruleBasedSavingsGoal.getId().toString()));

        given().port(port)
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/savings-goals/rule-based/" + ruleBasedSavingsGoal.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/savings-goals/rule-based")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(0));
    }
@Test
void givenRuleBasedSavingsGoalDto_whenPatchedSavingsGoal_thenSavingsGoalUpdate(){
    final var ruleBasedSavingsGoal = RuleBasedSavingsGoalDto.builder()
            .id(UUID.randomUUID())
            .name("Savings")
            .description("Monthly Savings")
            .achievementStatus(AchievementStatusDto.ACHIEVED)
            .matchingType(MatchingTypeDto.ALL)
            .rules(Collections.emptyList())
            .build();

    given().port(port)
            .contentType(ContentType.JSON)
            .body(ruleBasedSavingsGoal)
            .patch(LOCAL_BASE_URL_WITHOUT_PORT + "/savings-goals/rule-based/" + ruleBasedSavingsGoal.getId().toString())
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value());

    given().port(port)
            .contentType(ContentType.JSON)
            .body(ruleBasedSavingsGoal)
            .post(LOCAL_BASE_URL_WITHOUT_PORT + "/savings-goals/rule-based")
            .then()
            .statusCode(HttpStatus.CREATED.value());

    given().port(port)
            .contentType(ContentType.JSON)
            .body(ruleBasedSavingsGoal)
            .patch(LOCAL_BASE_URL_WITHOUT_PORT + "/savings-goals/rule-based/" + ruleBasedSavingsGoal.getId().toString())
            .then()
            .statusCode(HttpStatus.OK.value());
}


    @Test
    void givenRuleBasedSavingsGoalDto_whenCreateSavingsGoal_thenSavingsGoalExists(){
        final var ruleBasedSavingsGoal = RuleBasedSavingsGoalDto.builder()
                .id(UUID.randomUUID())
                .name("Savings")
                .description("Monthly Savings")
                .achievementStatus(AchievementStatusDto.ACHIEVED)
                .matchingType(MatchingTypeDto.ALL)
                .rules(Collections.emptyList())
                .build();

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/savings-goals/rule-based")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(0));

        given().port(port)
                .contentType(ContentType.JSON)
                .body(ruleBasedSavingsGoal)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/savings-goals/rule-based")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/savings-goals/rule-based")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(1))
                .and().body("[0].id", is(ruleBasedSavingsGoal.getId().toString()))
                .and().body("[0].name", is(ruleBasedSavingsGoal.getName()))
                .and().body("[0].description", is(ruleBasedSavingsGoal.getDescription()))
                .and().body("[0].achievementStatus", is(ruleBasedSavingsGoal.getAchievementStatus().toString()))
                .and().body("[0].matchingType", is(ruleBasedSavingsGoal.getMatchingType().toString()))
                .and().body("[0].rules.size()", is(0));

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/savings-goals/rule-based/" + ruleBasedSavingsGoal.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .body("id", Matchers.is(ruleBasedSavingsGoal.getId().toString()));
    }

}
