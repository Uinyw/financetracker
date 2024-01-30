package com.bank.account.api;

import com.bank.account.IntegrationTestBase;
import com.bank.account.data.TestBankAccountBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.model.MonetaryAmountDto;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

class BankResourceTest extends IntegrationTestBase {

    @BeforeEach
    void setUp() {
        doNothing().when(messagePublisher).publishMessageBankAccountUpdate(any());
    }

    @Test
    void givenBankAccountDto_whenCreateBankAccount_thenBankAccountExists() {
        final var bankAccountDto = TestBankAccountBuilder.buildWithDefaults();

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/bankAccounts")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(0));

        given().port(port)
                .contentType(ContentType.JSON)
                .body(bankAccountDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/bankAccounts")
                .then()
                .statusCode(HttpStatus.OK.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/bankAccounts")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(1))
                .and().body("[0].id", is(bankAccountDto.getId().toString()));
    }

    @Test
    void givenBankAccount_whenGetBankAccountById_thenBankAccountReturned() {
        final var bankAccountDto = TestBankAccountBuilder.buildWithDefaults();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(bankAccountDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/bankAccounts")
                .then()
                .statusCode(HttpStatus.OK.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/bankAccounts/" + bankAccountDto.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("id", is(bankAccountDto.getId().toString()));
    }

    @Test
    void givenBankAccount_whenDeleteBankAccountById_thenBankAccountDoesNotExistAnymore() {
        final var bankAccountDto = TestBankAccountBuilder.buildWithDefaults();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(bankAccountDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/bankAccounts")
                .then()
                .statusCode(HttpStatus.OK.value());

        given().port(port)
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/bankAccounts/" + bankAccountDto.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/bankAccounts")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(0));
    }

    @Test
    void givenBankAccount_whenPatchBankAccountById_thenBankAccountIsUpdated() {
        final var bankAccountDto = TestBankAccountBuilder.buildWithDefaults();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(bankAccountDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/bankAccounts")
                .then()
                .statusCode(HttpStatus.OK.value());

        final Double updatedBalance = bankAccountDto.getBalance().getAmount() + 100.0;
        bankAccountDto.setBalance(MonetaryAmountDto.builder().amount(updatedBalance).build());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(bankAccountDto)
                .patch(LOCAL_BASE_URL_WITHOUT_PORT + "/bankAccounts/" + bankAccountDto.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/bankAccounts/" + bankAccountDto.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("id", is(bankAccountDto.getId().toString()))
                .and().body("balance.amount", equalTo(updatedBalance.floatValue()));
    }
}
