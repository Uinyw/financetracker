package com.example.Analytics;

import com.example.Analytics.dietFunctionality.logic.model.Nutrition;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {

    public void createNutritionExcel(Nutrition nutritionData){
        try (Workbook workbook = new XSSFWorkbook()) {
            createNutritionSheet(workbook, nutritionData);

            try (FileOutputStream fileOut = new FileOutputStream("nutrition.xlsx")) {
                workbook.write(fileOut);
            }

            System.out.println("Excel file has been created successfully.");

        } catch (IOException e) {
            e.printStackTrace();
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

