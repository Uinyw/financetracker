package com.example.Analytics.logic.operations;

import com.example.Analytics.ExcelWriter;
import com.example.Analytics.logic.model.generalModel.FilterElement;
import com.example.Analytics.logic.model.generalModel.MoneyPerCategory;
import com.example.Analytics.logic.model.productModel.Duration;
import com.example.Analytics.logic.model.productModel.Nutrition;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        //TODO generate report
        //Nutrition nutritionData = generateDietReport(filter.getDuration());//TODO test with
        List<MoneyPerCategory> budgetData = budgetService.moneySpendPerCategory();
        excelWriter.createNutritionExcel(null, budgetData);
    }
    private Nutrition generateDietReport(Duration duration) {
        return dietService.getNutritionForDuration(duration);
    }


}
