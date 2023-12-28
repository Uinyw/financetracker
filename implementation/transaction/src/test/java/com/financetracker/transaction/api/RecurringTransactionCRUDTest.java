package com.financetracker.transaction.api;

import com.financetracker.transaction.IntegrationTestBase;
import com.financetracker.transaction.data.TestRecurringTransactionBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.BankAccountDto;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class RecurringTransactionCRUDTest extends IntegrationTestBase {

    @BeforeEach
    void setUp() {
        when(bankAccountProvider.getBankAccount(anyString())).thenReturn(Optional.of(new BankAccountDto()));
    }

    @Test
    void givenRecurringTransactionDto_whenCreateRecurringTransaction_thenRecurringTransactionExists() {
        final var recurringTransactionDto = TestRecurringTransactionBuilder.buildWithDefaults();

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(0));

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransactionDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(1))
                .and().body("[0].id", is(recurringTransactionDto.getId().toString()));
    }

    @Test
    void givenInvalidRecurringTransactionDto_whenCreateRecurringTransaction_thenBadRequest() {
        final var recurringTransactionDto = TestRecurringTransactionBuilder.buildWithDefaults();
        recurringTransactionDto.setPeriodicity(null);

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransactionDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void givenExistingRecurringTransaction_whenGetTransactionById_thenTransactionIsReturned() {
        final var recurringTransactionDto = TestRecurringTransactionBuilder.buildWithDefaults();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransactionDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransactionDto.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("id", is(recurringTransactionDto.getId().toString()))
                .and().body("type", is(recurringTransactionDto.getType().toString()));
    }

    @Test
    void givenExistingRecurringTransaction_whenGetTransactionByInvalidId_thenNotFound() {
        final var recurringTransactionDto = TestRecurringTransactionBuilder.buildWithDefaults();
        final var invalidId = "foo";

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransactionDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + invalidId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void givenExistingOneTimeTransaction_whenPatchTransaction_thenTransactionIsUpdated() {
        final var recurringTransactionDto = TestRecurringTransactionBuilder.buildWithDefaults();
        recurringTransactionDto.setName("Name");

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransactionDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransactionDto.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("name", is("Name"));

        recurringTransactionDto.setName("New Name");

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransactionDto)
                .patch(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransactionDto.getId().toString())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransactionDto.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("name", is("New Name"));
    }

    @Test
    void givenExistingRecurringTransaction_whenPatchTransactionByInvalidId_thenNotFound() {
        final var recurringTransactionDto = TestRecurringTransactionBuilder.buildWithDefaults();
        final var invalidId = "foo";

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransactionDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransactionDto)
                .patch(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + invalidId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void givenExistingRecurringTransaction_whenDeleteTransaction_thenTransactionDoesNotExistAnymore() {
        final var recurringTransactionDto = TestRecurringTransactionBuilder.buildWithDefaults();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransactionDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransactionDto.getId().toString())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(0));
    }

    @Test
    void givenExistingRecurringTransaction_whenDeleteTransactionByInvalidId_thenNotFound() {
        final var recurringTransactionDto = TestRecurringTransactionBuilder.buildWithDefaults();
        final var invalidId = "foo";

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransactionDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + invalidId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
