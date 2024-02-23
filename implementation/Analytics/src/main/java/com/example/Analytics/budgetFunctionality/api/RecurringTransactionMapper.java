package com.example.Analytics.budgetFunctionality.api;

import com.example.Analytics.budgetFunctionality.logic.model.FixedMonthlyTransaction;
import org.openapitools.client.model.RecurringTransactionDto;

public interface RecurringTransactionMapper {
    FixedMonthlyTransaction mapRecurringTransactionDtoToModel(final RecurringTransactionDto recurringTransactionDto);
}
