package com.financetracker.transaction.api;

import com.financetracker.transaction.IntegrationTestBase;
import com.financetracker.transaction.data.TestRecurringTransactionBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.BankAccountDto;
import org.openapitools.client.model.MonetaryAmountDto;
import org.openapitools.model.RecurringTransactionDto;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@Disabled
class RecurringTransactionTransferTest extends IntegrationTestBase {

    private RecurringTransactionDto recurringTransaction;

    @BeforeEach
    void setUp() {
        recurringTransaction = TestRecurringTransactionBuilder.buildWithDefaults();
    }

    @Test
    void givenBankAccountWithEnoughBalanceAndOneTimeTransaction_whenTransfer_thenTransferSucceeds() {
        doReturn(Optional.of(createBankAccount(100.0))).when(bankAccountProvider).getBankAccount(anyString());
        doReturn(true).when(bankAccountProvider).updateBankAccountBalance(any(), any());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("transactionRecords[0].transferStatus", is("INITIAL"));

        given().port(port)
                .contentType(ContentType.JSON)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransaction.getId().toString() + "/records/" + recurringTransaction.getTransactionRecords().get(0).getId().toString() + "/transfer")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("transactionRecords[0].transferStatus", is("SUCCESSFUL"));
    }


    private BankAccountDto createBankAccount(final Double balance) {
        return BankAccountDto.builder()
                .balance(MonetaryAmountDto.builder().amount(balance).build())
                .build();
    }
}
