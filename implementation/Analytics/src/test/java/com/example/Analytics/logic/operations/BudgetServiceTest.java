package com.example.Analytics.logic.operations;

import com.example.Analytics.IntegrationTestBase;
import com.example.Analytics.api.mapping.TransactionMapper;
import com.example.Analytics.infrastructure.db.VariableMonthlyTransactionRepository;
import com.example.Analytics.infrastructure.kafka.config.UpdateType;
import com.example.Analytics.logic.model.budgetModel.*;
import com.example.Analytics.logic.model.generalModel.MonetaryAmount;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BudgetServiceTest extends IntegrationTestBase {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private VariableMonthlyTransactionRepository variableMonthlyTransactionRepository;

    @Test
    void testVariableMonthlyTransactionChange() {
        final var variableMonthlyTransaction = VariableMonthlyTransaction.builder()
                .id(UUID.randomUUID())
                .name("Variable Transaction")
                .category(new Category("Food"))
                .build();

        final var transaction = Transaction.builder()
                .id(UUID.randomUUID())
                .referenceId(UUID.randomUUID())
                .date(LocalDate.now())
                .bankAccountSource(UUID.randomUUID())
                .bankAccountTarget(UUID.randomUUID())
                .variableMonthlyTransaction(variableMonthlyTransaction)
                .amount(new MonetaryAmount(20.0))
                .type(TransactionType.EXPENSE)
                .build();

        variableMonthlyTransaction.appendTransaction(transaction);

        variableMonthlyTransactionRepository.save(variableMonthlyTransaction);

        final var transactionDto = OneTimeTransactionDto.builder()
                .id(transaction.getReferenceId())
                .type(TypeDto.EXPENSE)
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("").build())
                .date(LocalDate.now().toString())
                .amount(MonetaryAmountDto.builder().amount(20.0).build())
                .labels(List.of("Food"))
                .build();

        final var transactionList = transactionMapper.oneTimeTransactionDtoToVariableMonthlyTransaction(transactionDto);

        budgetService.variableMonthlyTransactionChange(transactionList, UpdateType.DELETE);

        final var x = variableMonthlyTransactionRepository.findAll();
        assertEquals(0, x.size());

    }
}
