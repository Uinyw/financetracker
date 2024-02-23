package com.example.Analytics;

import com.example.Analytics.FilterElement;
import com.example.Analytics.dietFunctionality.logic.model.Diet;
import com.example.Analytics.dietFunctionality.logic.model.Duration;
import com.example.Analytics.dietFunctionality.logic.model.Nutrition;
import org.openapitools.client.ApiException;

import java.util.List;

public class Report {

    private FilterElement filter;
    private ExcelWriter excelWriter;
    private Diet diet;
    private void generateReport() throws ApiException {
        //TODO generate report
        generateDietReport(filter.getDuration());
    }
    public void generateDietReport(Duration duration) throws ApiException {
        Nutrition nutrition = diet.getNutritionForDuration(duration);
        excelWriter.createNutritionExcel(nutrition);
    }
}
