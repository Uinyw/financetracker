package com.example.Analytics.budgetFunctionality.api;

import com.example.Analytics.budgetFunctionality.logic.model.VariableMonthlyTransaction;
import org.openapitools.client.model.OneTimeTransactionDto;

public interface OneTimeTransactionMapper {
    VariableMonthlyTransaction mapOneTimeTransactionDtoToModel(final OneTimeTransactionDto oneTimeTransactionDto);

}
