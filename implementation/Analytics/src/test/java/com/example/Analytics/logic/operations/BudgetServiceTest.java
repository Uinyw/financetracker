package com.example.Analytics.logic.operations;

import com.example.Analytics.IntegrationTestBase;
import com.example.Analytics.infrastructure.kafka.config.UpdateType;
import org.junit.jupiter.api.Test;
import org.openapitools.model.OneTimeTransactionDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

class BudgetServiceTest extends IntegrationTestBase {

    @Autowired
    private BudgetService budgetService;

    @Test
    void testVariableMonthlyTransactionChange() {
        final var oneTimeTransactionDto = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .build();

        budgetService.variableMonthlyTransactionChange(oneTimeTransactionDto, UpdateType.UPDATE);

    }
}
