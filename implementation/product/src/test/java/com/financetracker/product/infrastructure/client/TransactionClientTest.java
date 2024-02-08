package com.financetracker.product.infrastructure.client;

import com.financetracker.product.api.exceptions.BookingException;
import com.financetracker.product.logic.model.MonetaryAmount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.client.ApiException;
import org.openapitools.client.api.OneTimeTransactionApi;
import org.openapitools.client.model.OneTimeTransactionDto;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TransactionClientTest {

    private TransactionClient transactionClient;

    private OneTimeTransactionApi transactionApi;

    @BeforeEach
    void setUp() {
        this.transactionApi = mock(OneTimeTransactionApi.class);
        this.transactionClient = new TransactionClient(Clock.systemUTC(), transactionApi);
    }

    @Test
    void givenTransactionApiDoesNotThrowException_whenBookExpense_thenNoBookingExceptionIsThrown() throws ApiException {
        doReturn("fakeUrl").when(transactionApi).getCustomBaseUrl();
        doNothing().when(transactionApi).transactionsOnetimePost(any(OneTimeTransactionDto.class));
        doNothing().when(transactionApi).transactionsOnetimeIdTransferPost(anyString());

        assertDoesNotThrow(() -> transactionClient.bookExpense(UUID.randomUUID(), new MonetaryAmount(BigDecimal.TEN)));
        verify(transactionApi, times(1)).transactionsOnetimePost(any(OneTimeTransactionDto.class));
        verify(transactionApi, times(1)).transactionsOnetimeIdTransferPost(anyString());
    }

    @Test
    void givenTransactionApiThrowsException_whenBookExpense_thenBookingExceptionIsThrown() throws ApiException {
        doReturn("fakeUrl").when(transactionApi).getCustomBaseUrl();
        doThrow(ApiException.class).when(transactionApi).transactionsOnetimePost(any(OneTimeTransactionDto.class));

        assertThrows(BookingException.class, () -> transactionClient.bookExpense(UUID.randomUUID(), new MonetaryAmount(BigDecimal.TEN)));
    }
}
