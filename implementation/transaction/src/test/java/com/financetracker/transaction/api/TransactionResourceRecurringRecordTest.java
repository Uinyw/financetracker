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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

class TransactionResourceRecurringRecordTest extends IntegrationTestBase {

    @Test
    void givenRecurringTransaction_whenCreateRecurringTransactionRecordForValidTransaction_thenTransactionRecordExistsInSystem() {
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


        final var transactionRecord = TransactionRecordDto.builder()
                .id(UUID.randomUUID())
                .date(LocalDate.now().toString())
                .amount(MonetaryAmountDto.builder().amount(1300.0).build())
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(transactionRecord)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransaction.getId().toString() + "/records")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(1))
                .and().body("[0].id", is(recurringTransaction.getId().toString()))
                .and().body("[0].transactionRecords.size()", is(1))
                .and().body("[0].transactionRecords[0].id", is(transactionRecord.getId().toString()))
                .and().body("[0].transactionRecords[0].date", is(transactionRecord.getDate()))
                .and().body("[0].transactionRecords[0].amount.amount", is(1300.0F));
    }

    @Test
    void givenRecurringTransaction_whenCreateRecurringTransactionRecordForInvalidTransaction_thenNotFound() {
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


        final var transactionRecord = TransactionRecordDto.builder()
                .id(UUID.randomUUID())
                .date(LocalDate.now().toString())
                .amount(MonetaryAmountDto.builder().amount(1300.0).build())
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(transactionRecord)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + UUID.randomUUID() + "/records")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(1))
                .and().body("[0].id", is(recurringTransaction.getId().toString()))
                .and().body("[0].transactionRecords.size()", is(0));
    }

    @Test
    void givenRecurringTransactionWithRecord_whenDeleteRecordForValidTransaction_thenRecordIsRemovedFromTransaction() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var transactionRecord = TransactionRecordDto.builder()
                .id(UUID.randomUUID())
                .date(LocalDate.now().toString())
                .amount(MonetaryAmountDto.builder().amount(1300.0).build())
                .build();

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
                .transactionRecords(List.of(transactionRecord))
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
                .and().body("[0].transactionRecords.size()", is(1))
                .and().body("[0].transactionRecords[0].id", is(transactionRecord.getId().toString()));

        given().port(port)
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransaction.getId() + "/records/" + transactionRecord.getId())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(1))
                .and().body("[0].id", is(recurringTransaction.getId().toString()))
                .and().body("[0].transactionRecords.size()", is(0));
    }

    @Test
    void givenRecurringTransactionWithRecord_whenDeleteRecordForInvalidTransaction_thenNotFound() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var transactionRecord = TransactionRecordDto.builder()
                .id(UUID.randomUUID())
                .date(LocalDate.now().toString())
                .amount(MonetaryAmountDto.builder().amount(1300.0).build())
                .build();

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
                .transactionRecords(List.of(transactionRecord))
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
                .and().body("[0].transactionRecords.size()", is(1))
                .and().body("[0].transactionRecords[0].id", is(transactionRecord.getId().toString()));

        given().port(port)
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + UUID.randomUUID() + "/records/" + transactionRecord.getId())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(1))
                .and().body("[0].id", is(recurringTransaction.getId().toString()))
                .and().body("[0].transactionRecords.size()", is(1))
                .and().body("[0].transactionRecords[0].id", is(transactionRecord.getId().toString()));
    }

    @Test
    void givenRecurringTransactionWithRecord_whenDeleteRecordForInvalidRecordId_thenNotFound() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var transactionRecord = TransactionRecordDto.builder()
                .id(UUID.randomUUID())
                .date(LocalDate.now().toString())
                .amount(MonetaryAmountDto.builder().amount(1300.0).build())
                .build();

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
                .transactionRecords(List.of(transactionRecord))
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
                .and().body("[0].transactionRecords.size()", is(1))
                .and().body("[0].transactionRecords[0].id", is(transactionRecord.getId().toString()));

        given().port(port)
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransaction.getId() + "/records/" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(1))
                .and().body("[0].id", is(recurringTransaction.getId().toString()))
                .and().body("[0].transactionRecords.size()", is(1))
                .and().body("[0].transactionRecords[0].id", is(transactionRecord.getId().toString()));
    }
}
