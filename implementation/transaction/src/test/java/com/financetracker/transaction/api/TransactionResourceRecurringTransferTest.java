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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

class TransactionResourceRecurringTransferTest extends IntegrationTestBase {

    @Test
    void givenBankAccountWithEnoughBalanceAndRecurringExpense_whenTransfer_thenTransferSucceeds() {
        doReturn(Optional.of(createBankAccount(500.0))).when(bankAccountProvider).getBankAccount(anyString());
        doReturn(true).when(bankAccountProvider).updateBankAccountBalance(any(), any());

        final var transactionRecord = TransactionRecordDto.builder()
                .id(UUID.randomUUID())
                .date(LocalDate.now().toString())
                .amount(MonetaryAmountDto.builder().amount(300.0).build())
                .build();

        final var recurringExpense = RecurringTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Rent")
                .description("Landlord: HaDiKo")
                .type(TypeDto.EXPENSE)
                .startDate(LocalDate.now().toString())
                .fixedAmount(MonetaryAmountDto.builder().amount(300.0).build())
                .periodicity(PeriodicityDto.MONTHLY)
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("HaDiKo").build())
                .transactionRecords(List.of(transactionRecord))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringExpense)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringExpense.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("transactionRecords[0].transferStatus", is("INITIAL"));

        given().port(port)
                .contentType(ContentType.JSON)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringExpense.getId().toString() + "/records/" + transactionRecord.getId() + "/transfer")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringExpense.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("transactionRecords[0].transferStatus", is("SUCCESSFUL"));
    }

    private BankAccountDto createBankAccount(final Double balance) {
        return BankAccountDto.builder()
                .balance(org.openapitools.client.model.MonetaryAmountDto.builder().amount(balance).build())
                .build();
    }
}
