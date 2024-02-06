package com.example.Analytics.DietFunctionality.DietMapper;

import com.example.Analytics.DietFunctionality.Consumption;
import com.example.Analytics.DietFunctionality.Nutrition;
import com.example.Analytics.DietFunctionality.Product;
import org.openapitools.client.model.NutritionDto;
import org.openapitools.client.model.ProductDto;

import java.util.UUID;

public class DietMapper {
    public Product productFromDTO(ProductDto productDto){
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .nutrition(nutritionFromDTO(productDto.getNutrition()))
                .build();

        //.size();//TODO how to get this
        //.consumption(); //TODO calculation,..
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
}
