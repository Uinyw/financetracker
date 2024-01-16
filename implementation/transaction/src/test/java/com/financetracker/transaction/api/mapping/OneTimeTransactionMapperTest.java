package com.financetracker.transaction.api.mapping;

import com.financetracker.transaction.IntegrationTestBase;
import com.financetracker.transaction.api.exceptions.NotParseableException;
import com.financetracker.transaction.data.TestOneTimeTransactionBuilder;
import com.financetracker.transaction.logic.model.*;
import org.junit.jupiter.api.Test;
import org.openapitools.model.MonetaryAmountDto;
import org.openapitools.model.OneTimeTransactionDto;
import org.openapitools.model.TransferDto;
import org.openapitools.model.TypeDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OneTimeTransactionMapperTest extends IntegrationTestBase {

    @Test
    void givenValidOneTimeTransactionDto_whenMappingToModel_thenNoExceptionIsThrownAndResultingModelIsValid() {
        final OneTimeTransactionDto oneTimeTransactionDto = TestOneTimeTransactionBuilder.build(
                "Transaction",
                "Transaction Description",
                TypeDto.INCOME,
                List.of("Label1", "Label2"),
                TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).targetBankAccountId(UUID.randomUUID()).build(),
                MonetaryAmountDto.builder().amount(10.0).build(),
                "2023-10-03"
        );

        final OneTimeTransaction oneTimeTransaction = oneTimeTransactionMapper.mapOneTimeTransactionDtoToModel(oneTimeTransactionDto);

        assertThat(oneTimeTransaction.getId(), is(oneTimeTransactionDto.getId().toString()));
        assertThat(oneTimeTransaction.getName(), is(oneTimeTransactionDto.getName()));
        assertThat(oneTimeTransaction.getDescription(), is(oneTimeTransactionDto.getDescription()));
        assertThat(oneTimeTransaction.getType(), is(Type.INCOME));

        assertThat(oneTimeTransaction.getLabels().stream().map(Label::name).collect(Collectors.toSet()), hasItems("Label1", "Label2"));

        assertThat(oneTimeTransaction.getTransfer().getSourceBankAccountId(), is(oneTimeTransactionDto.getTransfer().getSourceBankAccountId().toString()));
        assertNull(oneTimeTransaction.getTransfer().getExternalSourceId());
        assertThat(oneTimeTransaction.getTransfer().getTargetBankAccountId(), is(oneTimeTransactionDto.getTransfer().getTargetBankAccountId().toString()));

        assertThat(oneTimeTransaction.getAmount().amount(), equalTo(new BigDecimal("10.0")));

        assertThat(oneTimeTransaction.getDate(), equalTo(LocalDate.of(2023, 10, 3)));
    }

    @Test
    void givenOneTimeTransactionDtoWithOnlyRequiredProperties_whenMappingToModel_thenNoExceptionIsThrownAndResultingModelIsValid() {
        final OneTimeTransactionDto oneTimeTransactionDto = TestOneTimeTransactionBuilder.build(
                null,
                null,
                TypeDto.INCOME,
                null,
                TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).targetBankAccountId(UUID.randomUUID()).build(),
                MonetaryAmountDto.builder().amount(10.0).build(),
                "2023-10-03"
        );

        final OneTimeTransaction oneTimeTransaction = oneTimeTransactionMapper.mapOneTimeTransactionDtoToModel(oneTimeTransactionDto);

        assertThat(oneTimeTransaction.getId(), is(oneTimeTransactionDto.getId().toString()));
        assertNull(oneTimeTransaction.getName());
        assertNull(oneTimeTransaction.getDescription());
        assertThat(oneTimeTransaction.getType(), is(Type.INCOME));

        assertThat(oneTimeTransaction.getLabels().size(), is(0));

        assertThat(oneTimeTransaction.getTransfer().getSourceBankAccountId(), is(oneTimeTransactionDto.getTransfer().getSourceBankAccountId().toString()));
        assertNull(oneTimeTransaction.getTransfer().getExternalSourceId());
        assertThat(oneTimeTransaction.getTransfer().getTargetBankAccountId(), is(oneTimeTransactionDto.getTransfer().getTargetBankAccountId().toString()));

        assertThat(oneTimeTransaction.getAmount().amount(), equalTo(new BigDecimal("10.0")));

        assertThat(oneTimeTransaction.getDate(), equalTo(LocalDate.of(2023, 10, 3)));
    }

    @Test
    void givenOneTimeTransactionDtoWithInvalidTransactionType_whenMappingToModel_thenExceptionIsThrown() {
        final OneTimeTransactionDto oneTimeTransactionDto = TestOneTimeTransactionBuilder.build(
                null,
                null,
                null,
                null,
                TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).targetBankAccountId(UUID.randomUUID()).build(),
                MonetaryAmountDto.builder().amount(10.0).build(),
                "2023-10-03"
        );

        assertThrows(NotParseableException.class, () -> oneTimeTransactionMapper.mapOneTimeTransactionDtoToModel(oneTimeTransactionDto));
    }

    @Test
    void givenOneTimeTransactionDtoWithInvalidTransfer_whenMappingToModel_thenExceptionIsThrown() {
        final OneTimeTransactionDto oneTimeTransactionDto = TestOneTimeTransactionBuilder.build(
                null,
                null,
                TypeDto.INCOME,
                null,
                null,
                MonetaryAmountDto.builder().amount(10.0).build(),
                "2023-10-03"
        );

        assertThrows(NotParseableException.class, () -> oneTimeTransactionMapper.mapOneTimeTransactionDtoToModel(oneTimeTransactionDto));

    }

    @Test
    void givenOneTimeTransactionDtoWithInvalidDate_whenMappingToModel_thenExceptionIsThrown() {
        final OneTimeTransactionDto oneTimeTransactionDto = TestOneTimeTransactionBuilder.build(
                null,
                null,
                TypeDto.INCOME,
                null,
                TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).targetBankAccountId(UUID.randomUUID()).build(),
                MonetaryAmountDto.builder().amount(10.0).build(),
                "2023"
        );

        assertThrows(NotParseableException.class, () -> oneTimeTransactionMapper.mapOneTimeTransactionDtoToModel(oneTimeTransactionDto));
    }

    @Test
    void givenValidOneTimeTransactionModel_whenMappingToDto_thenNoExceptionIsThrownAndResultingDtoIsValid() {
        final OneTimeTransaction oneTimeTransaction = OneTimeTransaction.with()
                .id(UUID.randomUUID().toString())
                .name("Transaction")
                .description("Transaction Description")
                .type(Type.INCOME)
                .labels(Set.of(new Label("Label1"), new Label("Label2")))
                .transfer(new Transfer(UUID.randomUUID().toString(), null, UUID.randomUUID().toString(), null))
                .amount(new MonetaryAmount(new BigDecimal("10.0")))
                .date(LocalDate.of(2023, 10, 3))
                .build();

        final OneTimeTransactionDto oneTimeTransactionDto = oneTimeTransactionMapper.mapOneTimeTransactionModelToDto(oneTimeTransaction);

        assertThat(oneTimeTransactionDto.getId(), is(UUID.fromString(oneTimeTransaction.getId())));
        assertThat(oneTimeTransactionDto.getName(), is(oneTimeTransaction.getName()));
        assertThat(oneTimeTransactionDto.getDescription(), is(oneTimeTransaction.getDescription()));
        assertThat(oneTimeTransactionDto.getType(), is(TypeDto.INCOME));

        assertThat(oneTimeTransactionDto.getLabels(), hasItems("Label1", "Label2"));

        assertThat(oneTimeTransactionDto.getTransfer().getSourceBankAccountId(), is(UUID.fromString(oneTimeTransaction.getTransfer().getSourceBankAccountId())));
        assertNull(oneTimeTransactionDto.getTransfer().getExternalSourceId());
        assertThat(oneTimeTransactionDto.getTransfer().getTargetBankAccountId(), is(UUID.fromString(oneTimeTransaction.getTransfer().getTargetBankAccountId())));
        assertNull(oneTimeTransactionDto.getTransfer().getExternalTargetId());

        assertThat(oneTimeTransactionDto.getAmount().getAmount(), equalTo(10.0));

        assertThat(oneTimeTransactionDto.getDate(), is("2023-10-03"));
    }
}
