package com.financetracker.transaction.api;

import com.financetracker.transaction.IntegrationTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.BankAccountDto;
import org.openapitools.model.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

class TransactionResourceRecurringTest extends IntegrationTestBase {

    @Test
    void givenRecurringTransactionAndBankAccountExists_whenCreateRecurringTransaction_thenTransactionExistsInSystem() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var recurringTransaction = RecurringTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Employment Income")
                .description("Employer: KIT")
                .type(TypeDto.INCOME)
                .startDate(LocalDate.now().toString())
                .fixedAmount(MonetaryAmountDto.builder().amount(1300.0).build())
                .periodicity(PeriodicityDto.MONTHLY)
                .transfer(TransferDto.builder().externalSourceId("KIT").targetBankAccountId(UUID.randomUUID()).build())
                .labels(List.of("KIT"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(1))
                .and().body("[0].id", is(recurringTransaction.getId().toString()))
                .and().body("[0].name", is(recurringTransaction.getName()))
                .and().body("[0].description", is(recurringTransaction.getDescription()))
                .and().body("[0].startDate", is(recurringTransaction.getStartDate()))
                .and().body("[0].type", is(recurringTransaction.getType().toString()))
                .and().body("[0].periodicity", is(recurringTransaction.getPeriodicity().toString()))
                .and().body("[0].fixedAmount.amount", is(1300.0F))
                .and().body("[0].transfer.sourceBankAccountId", is(nullValue()))
                .and().body("[0].transfer.externalSourceId", is(recurringTransaction.getTransfer().getExternalSourceId()))
                .and().body("[0].transfer.targetBankAccountId", is(recurringTransaction.getTransfer().getTargetBankAccountId().toString()))
                .and().body("[0].transfer.externalTargetId", is(nullValue()))
                .and().body("[0].labels.size()", is(1))
                .and().body("[0].labels[0]", is("KIT"));
    }

    @Test
    void givenRecurringTransactionAndBankAccountDoesNotExist_whenCreateRecurringTransaction_thenBadRequest() {
        doReturn(Optional.empty()).when(bankAccountProvider).getBankAccount(anyString());

        final var recurringTransaction = RecurringTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Employment Income")
                .description("Employer: KIT")
                .type(TypeDto.INCOME)
                .startDate(LocalDate.now().toString())
                .fixedAmount(MonetaryAmountDto.builder().amount(1300.0).build())
                .periodicity(PeriodicityDto.MONTHLY)
                .transfer(TransferDto.builder().externalSourceId("KIT").targetBankAccountId(UUID.randomUUID()).build())
                .labels(List.of("KIT"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(0));
    }

    @Test
    void givenExistingRecurringTransaction_whenGetTransactionById_thenTransactionIsReturned() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var recurringTransaction = RecurringTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Rent")
                .description("Landlord: HaDiKo")
                .type(TypeDto.EXPENSE)
                .startDate(LocalDate.now().toString())
                .fixedAmount(MonetaryAmountDto.builder().amount(300.0).build())
                .periodicity(PeriodicityDto.MONTHLY)
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("HaDiKo").build())
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("id", is(recurringTransaction.getId().toString()));
    }

    @Test
    void givenExistingRecurringTransaction_whenGetTransactionByInvalidId_thenNotFound() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var recurringTransaction = RecurringTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Rent")
                .description("Landlord: HaDiKo")
                .type(TypeDto.EXPENSE)
                .startDate(LocalDate.now().toString())
                .fixedAmount(MonetaryAmountDto.builder().amount(300.0).build())
                .periodicity(PeriodicityDto.MONTHLY)
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("HaDiKo").build())
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void givenExistingRecurringTransaction_whenPatchTransaction_thenTransactionIsUpdated() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var recurringTransaction = RecurringTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Employment Income")
                .description("Employer: KIT")
                .type(TypeDto.INCOME)
                .startDate(LocalDate.now().toString())
                .fixedAmount(MonetaryAmountDto.builder().amount(1300.0).build())
                .periodicity(PeriodicityDto.MONTHLY)
                .transfer(TransferDto.builder().externalSourceId("KIT").targetBankAccountId(UUID.randomUUID()).build())
                .labels(List.of("KIT"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("name", is("Employment Income"));

        recurringTransaction.setName("New Employment Income");

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .patch(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("name", is("New Employment Income"));
    }

    @Test
    void givenExistingRecurringTransaction_whenPatchTransactionByInvalidId_thenNotFound() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var recurringTransaction = RecurringTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Employment Income")
                .description("Employer: KIT")
                .type(TypeDto.INCOME)
                .startDate(LocalDate.now().toString())
                .fixedAmount(MonetaryAmountDto.builder().amount(1300.0).build())
                .periodicity(PeriodicityDto.MONTHLY)
                .transfer(TransferDto.builder().externalSourceId("KIT").targetBankAccountId(UUID.randomUUID()).build())
                .labels(List.of("KIT"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        recurringTransaction.setName("New Employment Income");

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .patch(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("name", is("Employment Income"));
    }

    @Test
    void givenExistingRecurringTransactionAndBankAccountDoesNotExist_whenPatchTransaction_thenBadRequest() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var recurringTransaction = RecurringTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Employment Income")
                .description("Employer: KIT")
                .type(TypeDto.INCOME)
                .startDate(LocalDate.now().toString())
                .fixedAmount(MonetaryAmountDto.builder().amount(1300.0).build())
                .periodicity(PeriodicityDto.MONTHLY)
                .transfer(TransferDto.builder().externalSourceId("KIT").targetBankAccountId(UUID.randomUUID()).build())
                .labels(List.of("KIT"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        recurringTransaction.setName("New Employment Income");

        doReturn(Optional.empty()).when(bankAccountProvider).getBankAccount(anyString());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .patch(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("name", is("Employment Income"));
    }

    @Test
    void givenExistingRecurringTransaction_whenDeleteTransaction_thenTransactionDoesNotExistAnymore() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var recurringTransaction = RecurringTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Saving")
                .description("I want to save money")
                .type(TypeDto.SHIFT)
                .startDate(LocalDate.now().toString())
                .periodicity(PeriodicityDto.HALF_YEARLY)
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).targetBankAccountId(UUID.randomUUID()).build())
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(0));
    }

    @Test
    void givenExistingOneTimeTransaction_whenDeleteTransactionByInvalidId_thenNotFound() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var recurringTransaction = RecurringTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Saving")
                .description("I want to save money")
                .type(TypeDto.SHIFT)
                .startDate(LocalDate.now().toString())
                .periodicity(PeriodicityDto.HALF_YEARLY)
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).targetBankAccountId(UUID.randomUUID()).build())
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(1))
                .and().body("[0].id", is(recurringTransaction.getId().toString()));
    }

}
