package com.example.Analytics.api;

import com.example.Analytics.api.mapping.ProductMapper;
import com.example.Analytics.api.mapping.TransactionMapper;
import com.example.Analytics.logic.model.productModel.Duration;
import com.example.Analytics.logic.operations.BudgetService;
import com.example.Analytics.logic.operations.DietService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.BudgetPlanmonthlyAmountApi;
import org.openapitools.api.NutritionApi;
import org.openapitools.model.BudgetPlanDTO;
import org.openapitools.model.DurationDto;
import org.openapitools.model.NutritionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BudgettResource implements BudgetPlanmonthlyAmountApi {

    private final BudgetService budgetService;
    private final TransactionMapper transactionMapper;

    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<BudgetPlanDTO> budgetPlanmonthlyAmountGet(final String monthlyAmount) {
        double monthlyAount = Double.parseDouble(monthlyAmount);
        budgetService.calculateSavingPerCategory(monthlyAount);
        //TODO change
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
