package com.financetracker.transaction.api.mapping;

import com.financetracker.transaction.logic.model.RecurringTransaction;
import com.financetracker.transaction.logic.model.TransactionRecord;
import org.openapitools.model.RecurringTransactionDto;
import org.openapitools.model.TransactionRecordDto;

public interface RecurringTransactionMapper {
    RecurringTransactionDto mapRecurringTransactionModelToDto(final RecurringTransaction recurringTransaction);
    RecurringTransaction mapRecurringTransactionDtoToModel(final RecurringTransactionDto recurringTransactionDto);
    TransactionRecord mapTransactionRecordDtoToModel(final String transactionId, final TransactionRecordDto transactionRecordDto);
}
