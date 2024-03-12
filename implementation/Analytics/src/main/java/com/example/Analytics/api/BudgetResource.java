package com.example.Analytics.api;

import com.example.Analytics.api.mapping.BudgetMapper;
import com.example.Analytics.logic.model.budgetModel.BudgetPlan;
import com.example.Analytics.logic.operations.BudgetService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.BudgetPlanApi;
import org.openapitools.model.BudgetPlanDto;
import org.openapitools.model.ReportFilterDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
public class BudgetResource implements BudgetPlanApi {

    private final BudgetService budgetService;
    private final BudgetMapper budgetMapper;

    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<BudgetPlanDto> budgetPlanGet(final BigDecimal monthlyAmount, final ReportFilterDto reportFilterDto) {
        BudgetPlan budgetPlan = budgetService.createSavingsPlan(monthlyAmount.doubleValue(), budgetMapper.filterDtoToElement(reportFilterDto));
        return new ResponseEntity<>(budgetMapper.budgetPlanToDto(budgetPlan), HttpStatus.OK);
    }

}
