package com.financetracker.transaction.infrastructure.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.client.ApiException;
import org.openapitools.client.api.BankAccountApi;
import org.openapitools.client.model.BankAccountDto;
import org.openapitools.client.model.MonetaryAmountDto;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.is;

class BankAccountClientTest {

    private BankAccountClient bankAccountClient;

    private BankAccountApi bankAccountApi;

    @BeforeEach
    void setUp() {
        this.bankAccountApi = mock(BankAccountApi.class);
        this.bankAccountClient = new BankAccountClient(bankAccountApi);
    }

    @Test
    void givenBankAccountApiDoesNotThrowException_whenGetBankAccount_thenBankAccountIsReturned() throws ApiException {
        final var bankAccountId = UUID.randomUUID();
        doReturn("fakeUrl").when(bankAccountApi).getCustomBaseUrl();
        doReturn(BankAccountDto.builder().id(bankAccountId).build()).when(bankAccountApi).bankAccountsIdGet(bankAccountId.toString());

        final var bankAccount = bankAccountClient.getBankAccount(bankAccountId.toString());
        assertTrue(bankAccount.isPresent());
        assertThat(bankAccount.get().getId(), is(bankAccountId));

        verify(bankAccountApi, times(1)).bankAccountsIdGet(bankAccountId.toString());
    }

    @Test
    void givenBankAccountApiThrowsException_whenGetBankAccount_thenNoBankAccountIsReturned() throws ApiException {
        final var bankAccountId = UUID.randomUUID();
        doReturn("fakeUrl").when(bankAccountApi).getCustomBaseUrl();
        doThrow(ApiException.class).when(bankAccountApi).bankAccountsIdGet(bankAccountId.toString());

        final var bankAccount = bankAccountClient.getBankAccount(bankAccountId.toString());
        assertFalse(bankAccount.isPresent());

        verify(bankAccountApi, times(1)).bankAccountsIdGet(bankAccountId.toString());
    }

    @Test
    void givenBankAccountApiDoesNotThrowException_whenUpdateBankAccountBalance_thenUpdateSuccessful() throws ApiException {
        doNothing().when(bankAccountApi).bankAccountsIdPatch(any(), any());

        final var bankAccount = BankAccountDto.builder().id(UUID.randomUUID()).balance(MonetaryAmountDto.builder().amount(100.0).build()).build();
        final var result = bankAccountClient.updateBankAccountBalance(bankAccount, 10.0);

        assertTrue(result);

        verify(bankAccountApi, times(1)).bankAccountsIdPatch(any(), any());
    }

    @Test
    void givenBankAccountApiThrowsException_whenUpdateBankAccountBalance_thenUpdateIsNotSuccessful() throws ApiException {
        doThrow(ApiException.class).when(bankAccountApi).bankAccountsIdPatch(any(), any());

        final var bankAccount = BankAccountDto.builder().id(UUID.randomUUID()).balance(MonetaryAmountDto.builder().amount(100.0).build()).build();
        final var result = bankAccountClient.updateBankAccountBalance(bankAccount, 10.0);

        assertFalse(result);

        verify(bankAccountApi, times(1)).bankAccountsIdPatch(any(), any());
    }

}
