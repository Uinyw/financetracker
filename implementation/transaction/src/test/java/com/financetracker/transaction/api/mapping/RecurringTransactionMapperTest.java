package com.financetracker.transaction.api.mapping;

import com.financetracker.transaction.IntegrationTestBase;
import com.financetracker.transaction.data.TestOneTimeTransactionFactory;
import com.financetracker.transaction.logic.model.Label;
import com.financetracker.transaction.logic.model.OneTimeTransaction;
import com.financetracker.transaction.logic.model.Type;
import org.junit.jupiter.api.Test;
import org.openapitools.model.OneTimeTransactionDto;
import org.openapitools.model.TypeDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RecurringTransactionMapperTest extends IntegrationTestBase {

    @Test
    void givenValidRecurringTransactionDto_whenMappingToModel_thenNoExceptionIsThrownAndResultingModelIsValid() {
        final OneTimeTransactionDto oneTimeTransactionDto = TestOneTimeTransactionFactory.createDto(
                "Transaction",
                "Transaction Description",
                TypeDto.INCOME,
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
}
