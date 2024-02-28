package com.example.Analytics.logic.operations;

import com.example.Analytics.IntegrationTestBase;
import com.example.Analytics.infrastructure.kafka.config.UpdateType;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.OneTimeTransactionDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

class BudgetServiceTest extends IntegrationTestBase {

    @Autowired
    private BudgetService budgetService;

    @Test
    void testVariableMonthlyTransactionChange() {

        //budgetService.variableMonthlyTransactionChange(null, UpdateType.UPDATE);

    }
}
