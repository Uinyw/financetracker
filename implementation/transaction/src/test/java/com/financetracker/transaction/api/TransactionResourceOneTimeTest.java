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

class TransactionResourceOneTimeTest extends IntegrationTestBase {

    @Test
    void givenOneTimeTransactionAndBankAccountExists_whenCreateOneTimeTransaction_thenOneTimeTransactionExistsInSystem() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var oneTimeTransaction = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("OneTime Payment")
                .description("For pizza last evening.")
                .date(LocalDate.now().toString())
                .type(TypeDto.EXPENSE)
                .amount(MonetaryAmountDto.builder().amount(14.99).build())
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("Thomas BankAccount").build())
                .labels(List.of("Friend"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(1))
                .and().body("[0].id", is(oneTimeTransaction.getId().toString()))
                .and().body("[0].name", is(oneTimeTransaction.getName()))
                .and().body("[0].description", is(oneTimeTransaction.getDescription()))
                .and().body("[0].date", is(oneTimeTransaction.getDate()))
                .and().body("[0].type", is(oneTimeTransaction.getType().toString()))
                .and().body("[0].transferStatus", is(TransferStatusDto.INITIAL.toString()))
                .and().body("[0].amount.amount", is(14.99F))
                .and().body("[0].transfer.sourceBankAccountId", is(oneTimeTransaction.getTransfer().getSourceBankAccountId().toString()))
                .and().body("[0].transfer.externalSourceId", is(nullValue()))
                .and().body("[0].transfer.targetBankAccountId", is(nullValue()))
                .and().body("[0].transfer.externalTargetId", is(oneTimeTransaction.getTransfer().getExternalTargetId()))
                .and().body("[0].labels.size()", is(1))
                .and().body("[0].labels[0]", is("Friend"));
    }

    @Test
    void givenOneTimeTransactionAndBankAccountDoesNotExist_whenCreateOneTimeTransaction_thenBadRequest() {
        doReturn(Optional.empty()).when(bankAccountProvider).getBankAccount(anyString());

        final var oneTimeTransaction = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("OneTime Payment")
                .description("For pizza last evening.")
                .date(LocalDate.now().toString())
                .type(TypeDto.EXPENSE)
                .amount(MonetaryAmountDto.builder().amount(14.99).build())
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("Thomas BankAccount").build())
                .labels(List.of("Friend"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(0));
    }

    @Test
    void givenExistingOneTimeTransaction_whenGetTransactionByTargetBankAccount_thenTransactionIsReturned() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var oneTimeTransaction = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("OneTime Income")
                .description("For pizza last evening.")
                .date(LocalDate.now().toString())
                .type(TypeDto.INCOME)
                .amount(MonetaryAmountDto.builder().amount(14.99).build())
                .transfer(TransferDto.builder().externalSourceId("Marks BankAccount").targetBankAccountId(UUID.randomUUID()).build())
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime?targetBankAccount=" + oneTimeTransaction.getTransfer().getTargetBankAccountId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(1))
                .and().body("[0].id", is(oneTimeTransaction.getId().toString()));
    }

    @Test
    void givenExistingOneTimeTransaction_whenGetTransactionBySourceBankAccount_thenTransactionIsReturned() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var oneTimeTransaction = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("OneTime Payment")
                .description("For pizza last evening.")
                .date(LocalDate.now().toString())
                .type(TypeDto.EXPENSE)
                .amount(MonetaryAmountDto.builder().amount(14.99).build())
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("Thomas BankAccount").build())
                .labels(List.of("Friend"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime?sourceBankAccount=" + oneTimeTransaction.getTransfer().getSourceBankAccountId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(1))
                .and().body("[0].id", is(oneTimeTransaction.getId().toString()));
    }

    @Test
    void givenExistingOneTimeTransaction_whenGetTransactionById_thenTransactionIsReturned() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var oneTimeTransaction = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("OneTime Income")
                .description("For pizza last evening.")
                .date(LocalDate.now().toString())
                .type(TypeDto.INCOME)
                .amount(MonetaryAmountDto.builder().amount(14.99).build())
                .transfer(TransferDto.builder().externalSourceId("Marks BankAccount").targetBankAccountId(UUID.randomUUID()).build())
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("id", is(oneTimeTransaction.getId().toString()));
    }

    @Test
    void givenExistingOneTimeTransaction_whenGetTransactionByInvalidId_thenNotFound() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var oneTimeTransaction = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Savings Shift")
                .description("I want to save some money.")
                .date(LocalDate.now().toString())
                .type(TypeDto.SHIFT)
                .amount(MonetaryAmountDto.builder().amount(200.0).build())
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).targetBankAccountId(UUID.randomUUID()).build())
                .labels(List.of("SavingsGoal"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void givenExistingOneTimeTransaction_whenPatchTransaction_thenTransactionIsUpdated() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var oneTimeTransaction = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("OneTime Payment")
                .description("For pizza last evening.")
                .date(LocalDate.now().toString())
                .type(TypeDto.EXPENSE)
                .amount(MonetaryAmountDto.builder().amount(14.99).build())
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("Thomas BankAccount").build())
                .labels(List.of("Friend"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("name", is("OneTime Payment"));

        oneTimeTransaction.setName("New OneTime Payment");

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .patch(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("name", is("New OneTime Payment"));
    }

    @Test
    void givenExistingOneTimeTransaction_whenPatchTransactionByInvalidId_thenNotFound() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var oneTimeTransaction = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("OneTime Payment")
                .description("For pizza last evening.")
                .date(LocalDate.now().toString())
                .type(TypeDto.EXPENSE)
                .amount(MonetaryAmountDto.builder().amount(14.99).build())
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("Thomas BankAccount").build())
                .labels(List.of("Friend"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        oneTimeTransaction.setName("New OneTime Payment");

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .patch(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("name", is("OneTime Payment"));
    }

    @Test
    void givenExistingOneTimeTransactionAndBankAccountDoesNotExist_whenPatchTransaction_thenBadRequest() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var oneTimeTransaction = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("OneTime Payment")
                .description("For pizza last evening.")
                .date(LocalDate.now().toString())
                .type(TypeDto.EXPENSE)
                .amount(MonetaryAmountDto.builder().amount(14.99).build())
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("Thomas BankAccount").build())
                .labels(List.of("Friend"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        oneTimeTransaction.setName("New OneTime Payment");

        doReturn(Optional.empty()).when(bankAccountProvider).getBankAccount(anyString());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .patch(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("name", is("OneTime Payment"));
    }

    @Test
    void givenExistingOneTimeTransaction_whenDeleteTransaction_thenTransactionDoesNotExistAnymore() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var oneTimeTransaction = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("OneTime Payment")
                .description("For pizza last evening.")
                .date(LocalDate.now().toString())
                .type(TypeDto.EXPENSE)
                .amount(MonetaryAmountDto.builder().amount(14.99).build())
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("Thomas BankAccount").build())
                .labels(List.of("Friend"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(0));
    }

    @Test
    void givenExistingOneTimeTransaction_whenDeleteTransactionByInvalidId_thenNotFound() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var oneTimeTransaction = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("OneTime Payment")
                .description("For pizza last evening.")
                .date(LocalDate.now().toString())
                .type(TypeDto.EXPENSE)
                .amount(MonetaryAmountDto.builder().amount(14.99).build())
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("Thomas BankAccount").build())
                .labels(List.of("Friend"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(1))
                .and().body("[0].id", is(oneTimeTransaction.getId().toString()));
    }
}
