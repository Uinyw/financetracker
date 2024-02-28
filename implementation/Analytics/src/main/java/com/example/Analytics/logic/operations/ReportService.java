package com.example.Analytics.logic.operations;

import com.example.Analytics.ExcelWriter;
import com.example.Analytics.logic.model.budgetModel.BudgetPlan;
import com.example.Analytics.logic.model.generalModel.FilterElement;
import com.example.Analytics.logic.model.productModel.Duration;
import com.example.Analytics.logic.model.productModel.Nutrition;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ReportService {

    private FilterElement filter;
    @Autowired
    private ExcelWriter excelWriter;
    @Autowired
    private DietService dietService;
    @Autowired
    private BudgetService budgetService;

    public void generateReport(){
        this.filter = FilterElement.builder().bankAccountList(new ArrayList<>())
                .categoryList(new ArrayList<>())
                .duration(new Duration(LocalDate.now().minusYears(2).toString(),
                        LocalDate.now().toString()))
                .build();

        //TODO generate report, delete code above, use the bank account
        Nutrition nutritionData = generateDietReport(filter.getDuration());
        BudgetPlan budgetData = budgetService.spendingForEachCategory(filter);
        excelWriter.createNutritionExcel(nutritionData, budgetData);
    }
    private Nutrition generateDietReport(Duration duration) {
        return dietService.getNutritionForDuration(duration);
    }


}
