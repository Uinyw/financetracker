package com.financetracker.product.infrastructure.client;

import com.financetracker.product.api.exceptions.BookingException;
import com.financetracker.product.logic.model.MonetaryAmount;
import org.openapitools.client.ApiException;
import org.openapitools.client.api.OneTimeTransactionApi;
import org.openapitools.client.model.MonetaryAmountDto;
import org.openapitools.client.model.OneTimeTransactionDto;
import org.openapitools.client.model.TransferDto;
import org.openapitools.client.model.TypeDto;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionClient implements TransactionProvider {

    private static final String BASE_URL_TRANSACTION = "http://localhost:8080";

    private final Clock clock;
    private final OneTimeTransactionApi transactionApi;

    public TransactionClient(final Clock clock) {
        transactionApi = new OneTimeTransactionApi();
        transactionApi.setCustomBaseUrl(BASE_URL_TRANSACTION);

        this.clock = clock;
    }

    public void bookExpense(final UUID bankAccountId, final MonetaryAmount amount) {
        final String date = LocalDate.now(clock).toString();

        final var transaction = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Shopping Expense on " + date)
                .description("Shopping Expense")
                .type(TypeDto.EXPENSE)
                .date(date)
                .transfer(TransferDto.builder().sourceBankAccountId(bankAccountId).externalSourceId("Supermarket").build())
                .amount(MonetaryAmountDto.builder().amount(amount.amount().doubleValue()).build())
                .labels(List.of("Shopping Expense"))
                .build();
        try {
            transactionApi.transactionsOnetimePost(transaction);
            transactionApi.transactionsOnetimeIdTransferPost(transaction.getId().toString());
        } catch (ApiException e) {
            throw new BookingException();
        }
    }
}
