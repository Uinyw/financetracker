package com.financetracker.transaction.api.mapping;

import com.financetracker.transaction.IntegrationTestBase;
import com.financetracker.transaction.api.exceptions.NotParseableException;
import com.financetracker.transaction.data.TestRecurringTransactionBuilder;
import com.financetracker.transaction.logic.model.*;
import org.junit.jupiter.api.Test;
import org.openapitools.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RecurringTransactionMapperTest extends IntegrationTestBase {

    @Test
    void givenValidRecurringTransactionDto_whenMappingToModel_thenNoExceptionIsThrownAndResultingModelIsValid() {
        final var monetaryAmount = MonetaryAmountDto.builder().amount(10.0).build();
        final RecurringTransactionDto recurringTransactionDto = TestRecurringTransactionBuilder.build(
                "Transaction",
                "Transaction Description",
                TypeDto.INCOME,
                List.of("Label1", "Label2"),
                TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).targetBankAccountId(UUID.randomUUID()).build(),
                PeriodicityDto.MONTHLY,
                "2023-10-03",
                monetaryAmount,
                List.of(TransactionRecordDto.builder().id(UUID.randomUUID()).amount(monetaryAmount).date("2023-10-03").build())
        );

        final RecurringTransaction recurringTransaction = recurringTransactionMapper.mapRecurringTransactionDtoToModel(recurringTransactionDto);

        assertThat(recurringTransaction.getId(), is(recurringTransactionDto.getId().toString()));
        assertThat(recurringTransaction.getName(), is(recurringTransactionDto.getName()));
        assertThat(recurringTransaction.getDescription(), is(recurringTransactionDto.getDescription()));
        assertThat(recurringTransaction.getType(), is(Type.INCOME));

        assertThat(recurringTransaction.getLabels().stream().map(Label::name).collect(Collectors.toSet()), hasItems("Label1", "Label2"));

        assertThat(recurringTransaction.getTransfer().getSourceBankAccountId(), is(recurringTransactionDto.getTransfer().getSourceBankAccountId().toString()));
        assertNull(recurringTransaction.getTransfer().getExternalSourceId());
        assertThat(recurringTransaction.getTransfer().getTargetBankAccountId(), is(recurringTransactionDto.getTransfer().getTargetBankAccountId().toString()));

        assertThat(recurringTransaction.getPeriodicity(), is(Periodicity.MONTHLY));
        assertThat(recurringTransaction.getStartDate(), equalTo(LocalDate.of(2023, 10, 3)));
        assertThat(recurringTransaction.getFixedAmount().amount(), equalTo(new BigDecimal("10.0")));

        assertThat(recurringTransaction.getTransactionRecords().size(), is(1));
        assertThat(recurringTransaction.getTransactionRecords(), hasItems(hasProperty("amount", equalTo(new MonetaryAmount(BigDecimal.valueOf(recurringTransactionDto.getTransactionRecords().get(0).getAmount().getAmount()))))));
    }

    @Test
    void givenValidRecurringTransactionDtoWithOnlyRequiredProperties_whenMappingToModel_thenNoExceptionIsThrownAndResultingModelIsValid() {
        final RecurringTransactionDto recurringTransactionDto = TestRecurringTransactionBuilder.build(
                null,
                null,
                TypeDto.INCOME,
                null,
                TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).targetBankAccountId(UUID.randomUUID()).build(),
                PeriodicityDto.MONTHLY,
                "2023-10-03",
                null,
                null
        );

        final RecurringTransaction recurringTransaction = recurringTransactionMapper.mapRecurringTransactionDtoToModel(recurringTransactionDto);

        assertThat(recurringTransaction.getId(), is(recurringTransactionDto.getId().toString()));
        assertNull(recurringTransaction.getName());
        assertNull(recurringTransaction.getDescription());
        assertThat(recurringTransaction.getType(), is(Type.INCOME));

        assertThat(recurringTransaction.getLabels().size(), is(0));

        assertThat(recurringTransaction.getTransfer().getSourceBankAccountId(), is(recurringTransactionDto.getTransfer().getSourceBankAccountId().toString()));
        assertNull(recurringTransaction.getTransfer().getExternalSourceId());
        assertThat(recurringTransaction.getTransfer().getTargetBankAccountId(), is(recurringTransactionDto.getTransfer().getTargetBankAccountId().toString()));

        assertThat(recurringTransaction.getPeriodicity(), is(Periodicity.MONTHLY));
        assertThat(recurringTransaction.getStartDate(), equalTo(LocalDate.of(2023, 10, 3)));
        assertNull(recurringTransaction.getFixedAmount());

        assertThat(recurringTransaction.getTransactionRecords().size(), is(0));
    }

    @Test
    void giveRecurringTransactionDtoWithInvalidType_whenMappingToModel_thenNotParseableException() {
        final RecurringTransactionDto recurringTransactionDto = TestRecurringTransactionBuilder.build(
                null,
                null,
                null,
                null,
                TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).targetBankAccountId(UUID.randomUUID()).build(),
                PeriodicityDto.MONTHLY,
                "2023-10-03",
                null,
                null
        );

        assertThrows(NotParseableException.class, () -> recurringTransactionMapper.mapRecurringTransactionDtoToModel(recurringTransactionDto));
    }

    @Test
    void giveRecurringTransactionDtoWithInvalidTransfer_whenMappingToModel_thenNotParseableException() {
        final RecurringTransactionDto recurringTransactionDto = TestRecurringTransactionBuilder.build(
                null,
                null,
                TypeDto.INCOME,
                null,
                null,
                PeriodicityDto.MONTHLY,
                "2023-10-03",
                null,
                null
        );
        recurringTransactionDto.setTransfer(null);

        assertThrows(NotParseableException.class, () -> recurringTransactionMapper.mapRecurringTransactionDtoToModel(recurringTransactionDto));
    }

    @Test
    void giveRecurringTransactionDtoWithInvalidTransferSource_whenMappingToModel_thenNotParseableException() {
        final RecurringTransactionDto recurringTransactionDto = TestRecurringTransactionBuilder.build(
                null,
                null,
                TypeDto.INCOME,
                null,
                TransferDto.builder().sourceBankAccountId(null).targetBankAccountId(UUID.randomUUID()).build(),
                PeriodicityDto.MONTHLY,
                "2023-10-03",
                null,
                null
        );

        assertThrows(NotParseableException.class, () -> recurringTransactionMapper.mapRecurringTransactionDtoToModel(recurringTransactionDto));
    }

    @Test
    void giveRecurringTransactionDtoWithInvalidPeriodicity_whenMappingToModel_thenNotParseableException() {
        final RecurringTransactionDto recurringTransactionDto = TestRecurringTransactionBuilder.build(
                null,
                null,
                TypeDto.INCOME,
                null,
                TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).targetBankAccountId(UUID.randomUUID()).build(),
                null,
                "2023-10-03",
                null,
                null
        );

        assertThrows(NotParseableException.class, () -> recurringTransactionMapper.mapRecurringTransactionDtoToModel(recurringTransactionDto));
    }

    @Test
    void giveRecurringTransactionDtoWithInvalidStartDate_whenMappingToModel_thenNotParseableException() {
        final RecurringTransactionDto recurringTransactionDto = TestRecurringTransactionBuilder.build(
                null,
                null,
                TypeDto.INCOME,
                null,
                TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).targetBankAccountId(UUID.randomUUID()).build(),
                PeriodicityDto.MONTHLY,
                "2023",
                null,
                null
        );

        assertThrows(NotParseableException.class, () -> recurringTransactionMapper.mapRecurringTransactionDtoToModel(recurringTransactionDto));

        recurringTransactionDto.startDate(null);
        assertThrows(NotParseableException.class, () -> recurringTransactionMapper.mapRecurringTransactionDtoToModel(recurringTransactionDto));
    }
}
