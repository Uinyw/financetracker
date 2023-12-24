package com.financetracker.transaction.data;

import org.openapitools.model.*;

import java.util.List;
import java.util.UUID;

public class TestRecurringTransactionFactory {

    public static RecurringTransactionDto createDto() {
        final double amount = 10.0;
        final String date = "2023-10-03";

        final var amountDto = new MonetaryAmountDto();
        amountDto.setAmount(amount);
        final TransactionRecordDto transactionRecordDto = new TransactionRecordDto();
        transactionRecordDto.setAmount(amountDto);
        transactionRecordDto.setDate(date);

        return createDto(null,
                null,
                TypeDto.INCOME,
                null,
                UUID.randomUUID(),
                null,
                PeriodicityDto.MONTHLY,
                date,
                amount,
                List.of(transactionRecordDto));
    }

    public static RecurringTransactionDto createDto(final String name,
                                                    final String description,
                                                    final TypeDto type,
                                                    final List<String> labels,
                                                    final UUID sourceBankAccountId,
                                                    final String externalSourceId,
                                                    final PeriodicityDto periodicity,
                                                    final String startDate,
                                                    final double fixedAmount,
                                                    final List<TransactionRecordDto> transactionRecords) {
        final var result = new RecurringTransactionDto();
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

        final var fixedAmountDto = new MonetaryAmountDto();
        fixedAmountDto.setAmount(fixedAmount);
        result.setFixedAmount(fixedAmountDto);

        result.setPeriodicity(periodicity);
        result.setStartDate(startDate);
        result.setTransactionRecords(transactionRecords);

        return result;
    }
}
