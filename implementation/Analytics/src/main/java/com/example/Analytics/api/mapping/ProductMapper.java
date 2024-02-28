package com.example.Analytics.api.mapping;

import com.example.Analytics.logic.model.productModel.Consumption;
import com.example.Analytics.logic.model.productModel.Nutrition;
import com.example.Analytics.logic.model.productModel.Product;
import org.openapitools.client.model.NutritionDto;
import org.openapitools.client.model.ProductDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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
                .size(productDto.getSize().doubleValue())
                .build();
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

    public org.openapitools.model.NutritionDto nutritionDtoFromNutrition(Nutrition nutrition){
        return org.openapitools.model.NutritionDto.builder()
                .sugar(nutrition.getSugar())
                .calories(nutrition.getCalories())
                .carbohydrates(nutrition.getCarbohydrates())
                .fat(nutrition.getFat())
                .protein(nutrition.getProtein())
                .servingSize(nutrition.getServingSize())
                .build();
    }

    private Boolean isNutritionDTOValid(NutritionDto nutrition){
        return nutrition != null && nutrition.getServingSize() != null
                && nutrition.getCalories() != null && nutrition.getProtein() != null
                && nutrition.getCarbohydrates() != null && nutrition.getFat() != null
                && nutrition.getSugar() != null;
    }

}
