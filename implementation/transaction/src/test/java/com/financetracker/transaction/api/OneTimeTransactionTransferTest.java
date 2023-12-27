package com.financetracker.transaction.api;

import com.financetracker.transaction.IntegrationTestBase;
import com.financetracker.transaction.data.TestBankAccountFactory;
import com.financetracker.transaction.data.TestOneTimeTransactionFactory;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.model.OneTimeTransactionDto;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class OneTimeTransactionTransferTest extends IntegrationTestBase {

    private OneTimeTransactionDto oneTimeTransactionDto;

    @BeforeEach
    void setUp() {
        oneTimeTransactionDto = TestOneTimeTransactionFactory.createDto();
    }

    @Test
    void givenBankAccountWithEnoughBalanceAndOneTimeTransaction_whenTransfer_thenTransferSucceeds() {
        doReturn(Optional.of(TestBankAccountFactory.createDto(100.0))).when(bankAccountProvider).getBankAccount(anyString());
        doReturn(true).when(bankAccountProvider).updateBankAccountBalance(any(), any());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransactionDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransactionDto)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransactionDto.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("transferStatus", is("INITIAL"));

        given().port(port)
                .contentType(ContentType.JSON)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransactionDto.getId().toString() + "/transfer")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransactionDto)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransactionDto.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("transferStatus", is("SUCCESSFUL"));
    }

    @Test
    void givenBankAccountWithNotEnoughBalanceAndOneTimeTransaction_whenTransfer_thenTransferFails() {
        doReturn(Optional.of(TestBankAccountFactory.createDto(0.0))).when(bankAccountProvider).getBankAccount(anyString());
        doReturn(true).when(bankAccountProvider).updateBankAccountBalance(any(), any());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransactionDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransactionDto)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransactionDto.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("transferStatus", is("INITIAL"));

        given().port(port)
                .contentType(ContentType.JSON)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransactionDto.getId().toString() + "/transfer")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransactionDto)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransactionDto.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("transferStatus", is("FAILED"));
    }

    @Test
    void givenNotExistingBankAccount_whenTransfer_thenTransferFails() {
        doReturn(Optional.of(TestBankAccountFactory.createDto(100.0))).when(bankAccountProvider).getBankAccount(anyString());
        doReturn(true).when(bankAccountProvider).updateBankAccountBalance(any(), any());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransactionDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransactionDto)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransactionDto.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("transferStatus", is("INITIAL"));

        doReturn(Optional.empty()).when(bankAccountProvider).getBankAccount(anyString());

        given().port(port)
                .contentType(ContentType.JSON)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransactionDto.getId().toString() + "/transfer")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransactionDto)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransactionDto.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("transferStatus", is("FAILED"));
    }

    @Test
    void givenBankAccountAndUpdateBalanceFails_whenTransfer_thenTransferFails() {
        doReturn(Optional.of(TestBankAccountFactory.createDto(0.0))).when(bankAccountProvider).getBankAccount(anyString());
        doReturn(false).when(bankAccountProvider).updateBankAccountBalance(any(), any());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransactionDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransactionDto)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransactionDto.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("transferStatus", is("INITIAL"));

        given().port(port)
                .contentType(ContentType.JSON)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransactionDto.getId().toString() + "/transfer")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransactionDto)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransactionDto.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("transferStatus", is("FAILED"));
    }
}
