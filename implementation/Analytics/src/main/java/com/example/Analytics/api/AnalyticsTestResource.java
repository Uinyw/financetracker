package com.example.Analytics.api;

import com.example.Analytics.api.mapping.TransactionMapper;
import com.example.Analytics.logic.model.budgetModel.VariableMonthlyTransaction;
import com.example.Analytics.logic.operations.BudgetService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.TestTransferApi;
import org.openapitools.client.model.MonetaryAmountDto;
import org.openapitools.client.model.TransferDto;
import org.openapitools.client.model.TransferStatusDto;
import org.openapitools.client.model.TypeDto;
import org.openapitools.model.OneTimeTransactionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AnalyticsTestResource implements TestTransferApi {

    private final BudgetService budgetService;

    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<Void> testTransferPost(final OneTimeTransactionDto oneTimeTransactionDto) {
        budgetService.variableMonthlyTransactionChange(oneTimeTransactionDto, null);
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


}
