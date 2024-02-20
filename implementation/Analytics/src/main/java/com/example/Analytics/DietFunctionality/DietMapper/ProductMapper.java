package com.example.Analytics.DietFunctionality.DietMapper;

import com.example.Analytics.BudgetFunctionality.DateConverter;
import com.example.Analytics.DietFunctionality.Consumption;
import com.example.Analytics.DietFunctionality.Nutrition;
import com.example.Analytics.DietFunctionality.Product;
import org.openapitools.client.model.NutritionDto;
import org.openapitools.client.model.ProductDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class ProductMapper {
    public Product productFromDTO(ProductDto productDto, double amount){
        Consumption consumption = Consumption.builder()
                .date(LocalDate.now())
                .amount(amount)
                .build();

        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .nutrition(nutritionFromDTO(productDto.getNutrition()))
                .consumption(consumption)
                .build();
        //.size();//TODO in product dto
    }
    public Nutrition nutritionFromDTO(NutritionDto nutrition){
        if(!isNutritionDTOValid(nutrition))
            return null;
        return Nutrition.builder()
                .servingSize(nutrition.getServingSize().doubleValue())
        .calories(nutrition.getCalories().doubleValue())
        .carbohydrates(nutrition.getCarbohydrates().doubleValue())
        .protein(nutrition.getProtein().doubleValue())
        .fat(nutrition.getFat().doubleValue())
        .sugar(nutrition.getSugar().doubleValue())
                .build();
    }

    private Boolean isNutritionDTOValid(NutritionDto nutrition){
        return nutrition != null && nutrition.getServingSize() != null
                && nutrition.getCalories() != null && nutrition.getProtein() != null
                && nutrition.getCarbohydrates() != null && nutrition.getFat() != null
                && nutrition.getSugar() != null;
    }
/*
    private com.example.Analytics.DietFunctionality.Nutrition nutritionDTOtoNutrition(NutritionDto nutritionDto){
        //TODO überarbeiten mit builder
        com.example.Analytics.DietFunctionality.Nutrition nutrition;
        assert nutritionDto.getCalories() != null;
        nutrition.setCalories(nutritionDto.getCalories().doubleValue());
        assert nutritionDto.getFat() != null;
        nutrition.setFat(nutritionDto.getFat().doubleValue());
        assert nutritionDto.getCarbohydrates() != null;
        nutrition.setCarbohydrates(nutritionDto.getCarbohydrates().doubleValue());
        assert nutritionDto.getProtein() != null;
        nutrition.setProtein(nutritionDto.getProtein().doubleValue());
        assert nutritionDto.getServingSize() != null;
        nutrition.setServingSize(nutritionDto.getServingSize().doubleValue());
        return nutrition;
    }*/
}
