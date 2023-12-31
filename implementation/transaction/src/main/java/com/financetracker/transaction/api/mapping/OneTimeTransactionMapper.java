package com.financetracker.transaction.api.mapping;

import com.financetracker.transaction.logic.model.OneTimeTransaction;
import org.openapitools.model.OneTimeTransactionDto;

public interface OneTimeTransactionMapper {
    OneTimeTransactionDto mapOneTimeTransactionModelToDto(final OneTimeTransaction oneTimeTransaction);
    OneTimeTransaction mapOneTimeTransactionDtoToModel(final OneTimeTransactionDto oneTimeTransactionDto);

}
