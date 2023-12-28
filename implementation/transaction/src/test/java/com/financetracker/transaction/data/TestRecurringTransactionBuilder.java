package com.financetracker.transaction.data;

import org.openapitools.model.*;

import java.util.List;
import java.util.UUID;

public class TestRecurringTransactionBuilder {

    public static RecurringTransactionDto buildWithDefaults() {
        final double amount = 10.0;
        final String date = "2023-10-03";

        return build(null,
                null,
                TypeDto.INCOME,
                null,
                TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).targetBankAccountId(UUID.randomUUID()).build(),
                PeriodicityDto.MONTHLY,
                date,
                MonetaryAmountDto.builder().amount(amount).build(),
                List.of(TransactionRecordDto.builder().id(UUID.randomUUID()).amount(MonetaryAmountDto.builder().amount(amount).build()).date(date).build()));
    }

    public static RecurringTransactionDto build(final String name,
                                                final String description,
                                                final TypeDto type,
                                                final List<String> labels,
                                                final TransferDto transfer,
                                                final PeriodicityDto periodicity,
                                                final String startDate,
                                                final MonetaryAmountDto fixedAmount,
                                                final List<TransactionRecordDto> transactionRecords) {

        return RecurringTransactionDto.builder()
                .id(UUID.randomUUID())
                .name(name)
                .description(description)
                .type(type)
                .labels(labels)
                .transfer(transfer)
                .periodicity(periodicity)
                .startDate(startDate)
                .fixedAmount(fixedAmount)
                .transactionRecords(transactionRecords)
                .build();
    }
}
