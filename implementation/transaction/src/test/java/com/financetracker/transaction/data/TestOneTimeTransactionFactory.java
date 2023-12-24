package com.financetracker.transaction.data;

import com.financetracker.transaction.logic.model.*;
import org.openapitools.model.MonetaryAmountDto;
import org.openapitools.model.OneTimeTransactionDto;
import org.openapitools.model.TransferDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TestOneTimeTransactionFactory {

    public static OneTimeTransactionDto createDto(final String name,
                                                  final String description,
                                                  final OneTimeTransactionDto.TypeEnum type,
                                                  final List<String> labels,
                                                  final UUID sourceBankAccountId,
                                                  final String externalSourceId,
                                                  final double amount,
                                                  final String date) {
        final var result = new OneTimeTransactionDto();
        result.setId(UUID.randomUUID());
        result.setName(name);
        result.setDescription(description);
        result.setType(type);
        result.setLabels(labels);

        final var transferDto = new TransferDto();
        transferDto.setSourceBankAccountId(sourceBankAccountId);
        transferDto.setExternalSourceId(externalSourceId);
        transferDto.setTargetBankAccountId(UUID.randomUUID());
        result.setTransfer(transferDto);

        final var monetaryAmountDto = new MonetaryAmountDto();
        monetaryAmountDto.setAmount(amount);
        result.setAmount(monetaryAmountDto);

        result.setDate(date);

        return result;
    }

    public static OneTimeTransactionDto createDto() {
        return createDto(null, null, OneTimeTransactionDto.TypeEnum.INCOME, null, UUID.randomUUID(), null, 10.0, "2023-10-03");
    }

    public static OneTimeTransaction createModel(final String name,
                                                 final String description,
                                                 final Type type,
                                                 final List<String> labels,
                                                 final String sourceBankAccountId,
                                                 final String externalSourceId,
                                                 final double amount,
                                                 final LocalDate date) {

        return OneTimeTransaction.with()
                .id(UUID.randomUUID().toString())
                .name(name)
                .description(description)
                .type(type)
                .labels(labels.stream().map(Label::new).collect(Collectors.toSet()))
                .transfer(new Transfer(sourceBankAccountId, externalSourceId, UUID.randomUUID().toString()))
                .amount(new MonetaryAmount(BigDecimal.valueOf(amount)))
                .date(date)
                .build();
    }

}
