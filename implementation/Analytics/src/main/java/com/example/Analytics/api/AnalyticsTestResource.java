package com.example.Analytics.api;

import com.example.Analytics.infrastructure.kafka.config.UpdateType;
import com.example.Analytics.logic.operations.BudgetService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.TestTransferApi;
import org.openapitools.api.TestTransferFixedApi;
import org.openapitools.model.OneTimeTransactionDto;
import org.openapitools.model.RecurringTransactionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class AnalyticsTestResource implements TestTransferApi, TestTransferFixedApi {

    private final BudgetService budgetService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return TestTransferApi.super.getRequest();
    }

    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<Void> testTransferPost(final OneTimeTransactionDto oneTimeTransactionDto) {
        budgetService.createRandomEntry(5);
        budgetService.variableMonthlyTransactionChange(oneTimeTransactionDto, UpdateType.UPDATE);
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<Void> testTransferFixedPost(final RecurringTransactionDto recurringTransactionDto) {
        budgetService.createRandomEntry(5);
        budgetService.fixedMonthlyTransactionChange(recurringTransactionDto, UpdateType.CREATE);
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
