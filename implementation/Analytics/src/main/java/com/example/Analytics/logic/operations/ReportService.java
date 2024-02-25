package com.example.Analytics.logic.operations;

import com.example.Analytics.ExcelWriter;
import com.example.Analytics.logic.model.generalModel.FilterElement;
import com.example.Analytics.logic.model.productModel.Duration;
import com.example.Analytics.logic.model.productModel.Nutrition;
import org.openapitools.client.ApiException;

public class ReportService {

    private FilterElement filter;
    private ExcelWriter excelWriter;
    private DietService dietService;
    private BudgetService budgetService;

    private void generateReport() throws ApiException {
        //TODO generate report
        generateDietReport(filter.getDuration());
    }
    public void generateDietReport(Duration duration) throws ApiException {
        Nutrition nutrition = dietService.getNutritionForDuration(duration);
        excelWriter.createNutritionExcel(nutrition);
    }
}
