package com.financetracker.transaction.data;

import org.openapitools.model.MonetaryAmountDto;
import org.openapitools.model.OneTimeTransactionDto;
import org.openapitools.model.TransferDto;
import org.openapitools.model.TypeDto;

import java.util.List;
import java.util.UUID;

public class TestOneTimeTransactionBuilder {

    public static OneTimeTransactionDto buildWithDefaults() {
        return build(null,
                null,
                TypeDto.INCOME,
                null,
                TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).targetBankAccountId(UUID.randomUUID()).build(),
                MonetaryAmountDto.builder().amount(10.0).build(),
                "2023-10-03");
    }

    public static OneTimeTransactionDto build(final String name,
                                              final String description,
                                              final TypeDto type,
                                              final List<String> labels,
                                              final TransferDto transfer,
                                              final MonetaryAmountDto amount,
                                              final String date) {

        return OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name(name)
                .description(description)
                .type(type)
                .labels(labels)
                .transfer(transfer)
                .amount(amount)
                .date(date)
                .build();
    }

}
