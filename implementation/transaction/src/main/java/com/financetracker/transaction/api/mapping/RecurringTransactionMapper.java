package com.financetracker.transaction.api.mapping;

import com.financetracker.transaction.logic.model.RecurringTransaction;
import org.openapitools.model.RecurringTransactionDto;

public interface RecurringTransactionMapper {
    RecurringTransactionDto mapRecurringTransactionModelToDto(final RecurringTransaction recurringTransaction);
    RecurringTransaction mapRecurringTransactionDtoToModel(final RecurringTransactionDto recurringTransactionDto);
}
