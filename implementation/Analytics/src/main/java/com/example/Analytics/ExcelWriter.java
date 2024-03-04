package com.example.Analytics;

import com.example.Analytics.logic.model.budgetModel.BudgetElement;
import com.example.Analytics.logic.model.budgetModel.BudgetPlan;
import com.example.Analytics.logic.model.productModel.Nutrition;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class ExcelWriter {
    
    public void createNutritionExcel(Nutrition nutritionData, BudgetPlan budgetData, BudgetPlan forecastData){
        try (Workbook workbook = new XSSFWorkbook()) {
            createNutritionSheet(workbook, nutritionData);
            createBudgetSheet(workbook, budgetData, "budget");
            createBudgetSheet(workbook, forecastData, "forecast");

            try (FileOutputStream fileOut = new FileOutputStream("Report.xlsx")) {
                workbook.write(fileOut);
            }

            System.out.println("Excel file has been created successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createBudgetSheet(Workbook workbook, BudgetPlan budgetData, String name) {
        Sheet sheet = workbook.createSheet(name);

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Category");
        headerRow.createCell(1).setCellValue("Amount in €");

        int rowNum = 1;
        for(BudgetElement budgetElement : budgetData.getBudgetElementList()){
            sheet.createRow(rowNum++).createCell(0).setCellValue(budgetElement.getCategory().getName());
            sheet.getRow(rowNum - 1).createCell(1).setCellValue(budgetElement.getMonetaryAmount().getAmount());
        }
    }

    private static void createNutritionSheet(Workbook workbook, Nutrition nutritionData) {
        Sheet sheet = workbook.createSheet("nutrition");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Nutrition");
        headerRow.createCell(1).setCellValue("Value in g");

        int rowNum = 1;
        sheet.createRow(rowNum++).createCell(0).setCellValue("Serving Size");
        sheet.getRow(rowNum - 1).createCell(1).setCellValue(nutritionData.getServingSize());

        sheet.createRow(rowNum++).createCell(0).setCellValue("Calories");
        sheet.getRow(rowNum - 1).createCell(1).setCellValue(nutritionData.getCalories());

        sheet.createRow(rowNum++).createCell(0).setCellValue("Carbohydrates");
        sheet.getRow(rowNum - 1).createCell(1).setCellValue(nutritionData.getCarbohydrates());

        sheet.createRow(rowNum++).createCell(0).setCellValue("Protein");
        sheet.getRow(rowNum - 1).createCell(1).setCellValue(nutritionData.getProtein());

        sheet.createRow(rowNum++).createCell(0).setCellValue("Fat");
        sheet.getRow(rowNum - 1).createCell(1).setCellValue(nutritionData.getFat());

        sheet.createRow(rowNum++).createCell(0).setCellValue("Sugar");
        sheet.getRow(rowNum - 1).createCell(1).setCellValue(nutritionData.getSugar());
    }
}

