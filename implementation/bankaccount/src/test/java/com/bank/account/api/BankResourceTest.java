package com.bank.account.api;

import com.bank.account.IntegrationTestBase;
import com.bank.account.infrastructure.kafka.MessagePublisher;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.model.BankAccountDto;
import org.openapitools.model.MonetaryAmountDto;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

class BankResourceTest extends IntegrationTestBase {

    @MockBean
    private MessagePublisher messagePublisher;

    @BeforeEach
    void setUp() {
        doNothing().when(messagePublisher).publishMessageBankAccountUpdate(any());
    }

    @Test
    void givenBankAccountDto_whenCreateBankAccount_thenBankAccountExists() {
        final var bankAccountDto = BankAccountDto.builder()
                .id(UUID.randomUUID())
                .name("Bank Account")
                .description("Description for Bank Account")
                .balance(MonetaryAmountDto.builder().amount(1.0).build())
                .dispositionLimit(MonetaryAmountDto.builder().amount(2.0).build())
                .labels(List.of("Label"))
                .build();

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
        final var bankAccountDto = BankAccountDto.builder()
                .id(UUID.randomUUID())
                .name("Bank Account")
                .description("Description for Bank Account")
                .balance(MonetaryAmountDto.builder().amount(1.0).build())
                .dispositionLimit(MonetaryAmountDto.builder().amount(2.0).build())
                .build();

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
        final var bankAccountDto = BankAccountDto.builder()
                .id(UUID.randomUUID())
                .name("Bank Account")
                .description("Description for Bank Account")
                .balance(MonetaryAmountDto.builder().amount(1.0).build())
                .dispositionLimit(MonetaryAmountDto.builder().amount(2.0).build())
                .labels(List.of("Label"))
                .build();

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
        final var bankAccountDto = BankAccountDto.builder()
                .id(UUID.randomUUID())
                .name("Bank Account")
                .description("Description for Bank Account")
                .balance(MonetaryAmountDto.builder().amount(1.0).build())
                .dispositionLimit(MonetaryAmountDto.builder().amount(2.0).build())
                .labels(List.of("Label"))
                .build();

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
