package com.financetracker.transaction.api;

import com.financetracker.transaction.IntegrationTestBase;
import com.financetracker.transaction.logic.operations.TransferService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.BankAccountDto;
import org.openapitools.model.MonetaryAmountDto;
import org.openapitools.model.OneTimeTransactionDto;
import org.openapitools.model.TransferDto;
import org.openapitools.model.TypeDto;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TransactionResourceOneTimeTransferTest extends IntegrationTestBase {

    @SpyBean
    private TransferService transferService;

    @Test
    void givenBankAccountWithEnoughBalanceAndOneTimeExpense_whenTransfer_thenTransferSucceeds() {
        doReturn(Optional.of(createBankAccount(100.0))).when(bankAccountProvider).getBankAccount(anyString());
        doReturn(true).when(bankAccountProvider).updateBankAccountBalance(any(), any());

        final var oneTimeExpense = OneTimeTransactionDto.builder()
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
                .body(oneTimeExpense)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeExpense)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeExpense.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("transferStatus", is("INITIAL"));

        given().port(port)
                .contentType(ContentType.JSON)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeExpense.getId().toString() + "/transfer")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeExpense)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeExpense.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("transferStatus", is("SUCCESSFUL"));
    }

    @Test
    void givenBankAccountWithNotEnoughBalanceAndOneTimeExpense_whenTransfer_thenTransferFails() {
        doReturn(Optional.of(createBankAccount(0.0))).when(bankAccountProvider).getBankAccount(anyString());
        doReturn(true).when(bankAccountProvider).updateBankAccountBalance(any(), any());

        final var oneTimeExpense = OneTimeTransactionDto.builder()
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
                .body(oneTimeExpense)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeExpense)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeExpense.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("transferStatus", is("INITIAL"));

        given().port(port)
                .contentType(ContentType.JSON)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeExpense.getId().toString() + "/transfer")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeExpense)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeExpense.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("transferStatus", is("FAILED"));
    }

    @Test
    void givenNotExistingBankAccountAndOneTimeIncome_whenTransfer_thenTransferFails() {
        doReturn(Optional.of(createBankAccount(100.0))).when(bankAccountProvider).getBankAccount(anyString());
        doReturn(true).when(bankAccountProvider).updateBankAccountBalance(any(), any());

        final var oneTimeIncome = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("OneTime Income")
                .description("For pizza last evening.")
                .date(LocalDate.now().toString())
                .type(TypeDto.INCOME)
                .amount(MonetaryAmountDto.builder().amount(14.99).build())
                .transfer(TransferDto.builder().externalSourceId("Marks BankAccount").targetBankAccountId(UUID.randomUUID()).build())
                .labels(List.of("Friend"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeIncome)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeIncome)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeIncome.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("transferStatus", is("INITIAL"));

        doReturn(Optional.empty()).when(bankAccountProvider).getBankAccount(anyString());

        given().port(port)
                .contentType(ContentType.JSON)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeIncome.getId().toString() + "/transfer")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeIncome)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeIncome.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("transferStatus", is("FAILED"));
    }

    @Test
    void givenBankAccountAndUpdateBalanceFails_whenTransfer_thenTransferFails() {
        doReturn(Optional.of(createBankAccount(100.0))).when(bankAccountProvider).getBankAccount(anyString());
        doReturn(false).when(bankAccountProvider).updateBankAccountBalance(any(), any());

        final var oneTimeIncome = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("OneTime Income")
                .description("For pizza last evening.")
                .date(LocalDate.now().toString())
                .type(TypeDto.INCOME)
                .amount(MonetaryAmountDto.builder().amount(14.99).build())
                .transfer(TransferDto.builder().externalSourceId("Marks BankAccount").targetBankAccountId(UUID.randomUUID()).build())
                .labels(List.of("Friend"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeIncome)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeIncome)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeIncome.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("transferStatus", is("INITIAL"));

        given().port(port)
                .contentType(ContentType.JSON)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeIncome.getId().toString() + "/transfer")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeIncome)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeIncome.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("transferStatus", is("FAILED"));
    }

    @Test
    void givenTransferredOneTimeTransaction_whenDeleteTransaction_thenTransactionIsRolledBack() {
        doReturn(Optional.of(createBankAccount(100.0))).when(bankAccountProvider).getBankAccount(anyString());
        doReturn(true).when(bankAccountProvider).updateBankAccountBalance(any(), any());

        final var oneTimeExpense = OneTimeTransactionDto.builder()
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
                .body(oneTimeExpense)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeExpense.getId().toString() + "/transfer")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeExpense.getId().toString())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        verify(transferService, times(1)).rollbackTransfer(any(), any());
    }

    @Test
    void givenTransferredOneTimeTransaction_whenDeleteTransactionButBankAccountDoesNotExistAnymore_thenBadRequest() {
        doReturn(Optional.of(createBankAccount(100.0))).when(bankAccountProvider).getBankAccount(anyString());
        doReturn(true).when(bankAccountProvider).updateBankAccountBalance(any(), any());

        final var oneTimeExpense = OneTimeTransactionDto.builder()
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
                .body(oneTimeExpense)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeExpense.getId().toString() + "/transfer")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        doReturn(Optional.empty()).when(bankAccountProvider).getBankAccount(anyString());

        given().port(port)
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeExpense.getId().toString())
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        verify(transferService, times(1)).rollbackTransfer(any(), any());
    }

    private BankAccountDto createBankAccount(final Double balance) {
        return BankAccountDto.builder()
                .balance(org.openapitools.client.model.MonetaryAmountDto.builder().amount(balance).build())
                .build();
    }

}
