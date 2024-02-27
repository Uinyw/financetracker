package com.example.Analytics.api;

import com.example.Analytics.api.mapping.TransactionMapper;
import com.example.Analytics.logic.model.budgetModel.BudgetPlan;
import com.example.Analytics.logic.operations.BudgetService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.BudgetPlanmonthlyAmountApi;
import org.openapitools.model.BudgetPlanDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BudgetResource implements BudgetPlanmonthlyAmountApi {

    private final BudgetService budgetService;
    private final TransactionMapper transactionMapper;

    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<BudgetPlanDTO> budgetPlanmonthlyAmountGet(final String monthlyAmount) {
        double amount = Double.parseDouble(monthlyAmount);
        BudgetPlan budgetPlan = budgetService.createSavingsPlan(amount);
        return new ResponseEntity<>(transactionMapper.budgetPlanToDto(budgetPlan), HttpStatus.OK);
    }

}
