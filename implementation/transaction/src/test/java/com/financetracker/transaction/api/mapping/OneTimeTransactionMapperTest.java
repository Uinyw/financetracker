package com.financetracker.transaction.api.mapping;

import com.financetracker.transaction.IntegrationTestBase;
import com.financetracker.transaction.api.exceptions.NotParseableException;
import com.financetracker.transaction.data.TestOneTimeTransactionFactory;
import com.financetracker.transaction.logic.model.Label;
import com.financetracker.transaction.logic.model.OneTimeTransaction;
import com.financetracker.transaction.logic.model.Type;
import org.junit.jupiter.api.Test;
import org.openapitools.model.OneTimeTransactionDto;

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

class OneTimeTransactionMapperTest extends IntegrationTestBase {

    @Test
    void givenValidOneTimeTransactionDto_whenMappingToModel_thenNoExceptionIsThrownAndResultingModelIsValid() {
        final OneTimeTransactionDto oneTimeTransactionDto = TestOneTimeTransactionFactory.createDto(
                "Transaction",
                "Transaction Description",
                OneTimeTransactionDto.TypeEnum.INCOME,
                List.of("Label1", "Label2"),
                UUID.randomUUID(),
                null,
                10.0,
                "2023-10-03"
        );

        final OneTimeTransaction oneTimeTransaction = oneTimeTransactionMapper.mapOneTimeTransactionDtoToModel(oneTimeTransactionDto);

        assertThat(oneTimeTransaction.getId(), is(oneTimeTransactionDto.getId().toString()));
        assertThat(oneTimeTransaction.getName(), is(oneTimeTransactionDto.getName()));
        assertThat(oneTimeTransaction.getDescription(), is(oneTimeTransactionDto.getDescription()));
        assertThat(oneTimeTransaction.getType(), is(Type.INCOME));

        assertThat(oneTimeTransaction.getLabels().stream().map(Label::name).collect(Collectors.toSet()), hasItems("Label1", "Label2"));

        assertThat(oneTimeTransaction.getTransfer().sourceBankAccountId(), is(oneTimeTransactionDto.getTransfer().getSourceBankAccountId().toString()));
        assertNull(oneTimeTransaction.getTransfer().externalSourceId());
        assertThat(oneTimeTransaction.getTransfer().targetBankAccountId(), is(oneTimeTransactionDto.getTransfer().getTargetBankAccountId().toString()));

        assertThat(oneTimeTransaction.getAmount().amount(), equalTo(new BigDecimal("10.0")));

        assertThat(oneTimeTransaction.getDate(), equalTo(LocalDate.of(2023, 10, 3)));
    }

    @Test
    void givenOneTimeTransactionDtoWithOnlyRequiredProperties_whenMappingToModel_thenNoExceptionIsThrownAndResultingModelIsValid() {
        final OneTimeTransactionDto oneTimeTransactionDto = TestOneTimeTransactionFactory.createDto(
                null,
                null,
                OneTimeTransactionDto.TypeEnum.INCOME,
                null,
                UUID.randomUUID(),
                null,
                10.0,
                "2023-10-03"
        );

        final OneTimeTransaction oneTimeTransaction = oneTimeTransactionMapper.mapOneTimeTransactionDtoToModel(oneTimeTransactionDto);

        assertThat(oneTimeTransaction.getId(), is(oneTimeTransactionDto.getId().toString()));
        assertNull(oneTimeTransaction.getName());
        assertNull(oneTimeTransaction.getDescription());
        assertThat(oneTimeTransaction.getType(), is(Type.INCOME));

        assertThat(oneTimeTransaction.getLabels().size(), is(0));

        assertThat(oneTimeTransaction.getTransfer().sourceBankAccountId(), is(oneTimeTransactionDto.getTransfer().getSourceBankAccountId().toString()));
        assertNull(oneTimeTransaction.getTransfer().externalSourceId());
        assertThat(oneTimeTransaction.getTransfer().targetBankAccountId(), is(oneTimeTransactionDto.getTransfer().getTargetBankAccountId().toString()));

        assertThat(oneTimeTransaction.getAmount().amount(), equalTo(new BigDecimal("10.0")));

        assertThat(oneTimeTransaction.getDate(), equalTo(LocalDate.of(2023, 10, 3)));
    }

    @Test
    void givenOneTimeTransactionDtoWithInvalidTransactionType_whenMappingToModel_thenExceptionIsThrown() {
        final OneTimeTransactionDto oneTimeTransactionDto = TestOneTimeTransactionFactory.createDto(
                null,
                null,
                null,
                null,
                UUID.randomUUID(),
                null,
                10.0,
                "2023-10-03"
        );

        assertThrows(NotParseableException.class, () -> oneTimeTransactionMapper.mapOneTimeTransactionDtoToModel(oneTimeTransactionDto));
    }

    @Test
    void givenOneTimeTransactionDtoWithInvalidTransfer_whenMappingToModel_thenExceptionIsThrown() {
        final OneTimeTransactionDto oneTimeTransactionDto = TestOneTimeTransactionFactory.createDto(
                null,
                null,
                OneTimeTransactionDto.TypeEnum.INCOME,
                null,
                null,
                null,
                10.0,
                "2023-10-03"
        );

        assertThrows(NotParseableException.class, () -> oneTimeTransactionMapper.mapOneTimeTransactionDtoToModel(oneTimeTransactionDto));

    }

    @Test
    void givenOneTimeTransactionDtoWithInvalidDate_whenMappingToModel_thenExceptionIsThrown() {
        final OneTimeTransactionDto oneTimeTransactionDto = TestOneTimeTransactionFactory.createDto(
                null,
                null,
                OneTimeTransactionDto.TypeEnum.INCOME,
                null,
                null,
                "External ID",
                10.0,
                "2023"
        );

        assertThrows(NotParseableException.class, () -> oneTimeTransactionMapper.mapOneTimeTransactionDtoToModel(oneTimeTransactionDto));
    }

    @Test
    void givenValidOneTimeTransactionModel_whenMappingToDto_thenNoExceptionIsThrownAndResultingDtoIsValid() {
        final OneTimeTransaction oneTimeTransaction = TestOneTimeTransactionFactory.createModel(
                "Transaction",
                "Transaction Description",
                Type.INCOME,
                List.of("Label1", "Label2"),
                UUID.randomUUID().toString(),
                null,
                10.0,
                LocalDate.of(2023, 10, 3)
        );

        final OneTimeTransactionDto oneTimeTransactionDto = oneTimeTransactionMapper.mapOneTimeTransactionModelToDto(oneTimeTransaction);

        assertThat(oneTimeTransactionDto.getId(), is(UUID.fromString(oneTimeTransaction.getId())));
        assertThat(oneTimeTransactionDto.getName(), is(oneTimeTransaction.getName()));
        assertThat(oneTimeTransactionDto.getDescription(), is(oneTimeTransaction.getDescription()));
        assertThat(oneTimeTransactionDto.getType(), is(OneTimeTransactionDto.TypeEnum.INCOME));

        assertThat(oneTimeTransactionDto.getLabels(), hasItems("Label1", "Label2"));

        assertThat(oneTimeTransactionDto.getTransfer().getSourceBankAccountId(), is(UUID.fromString(oneTimeTransaction.getTransfer().sourceBankAccountId())));
        assertNull(oneTimeTransactionDto.getTransfer().getExternalSourceId());
        assertThat(oneTimeTransactionDto.getTransfer().getTargetBankAccountId(), is(UUID.fromString(oneTimeTransaction.getTransfer().targetBankAccountId())));

        assertThat(oneTimeTransactionDto.getAmount().getAmount(), equalTo(10.0));

        assertThat(oneTimeTransactionDto.getDate(), is("2023-10-03"));
    }
}
