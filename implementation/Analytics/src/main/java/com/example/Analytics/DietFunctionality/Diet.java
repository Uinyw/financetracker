package com.example.Analytics.DietFunctionality;

import org.openapitools.client.ApiException;
import org.openapitools.client.api.ProductApi;
import org.openapitools.client.model.Duration;
import org.openapitools.client.model.Nutrition;
import org.openapitools.client.model.NutritionDto;
import org.openapitools.client.model.ProductDto;

import java.util.List;

public class Diet {

    private ProductApi productApi;

    public Nutrition getNutritionForDuration(Duration duration) throws ApiException {
        List<ProductDto> productList = productApi.productsGet();//TODO send request
        Nutrition nutrition = new Nutrition();

        for(ProductDto productDto: productList){
            //TODO amount of products
            if (productDto.getNutrition() != null)
                nutrition = addToNutrition(nutrition, nutritionDTOtoNutrition(productDto.getNutrition()));
        }
        return nutrition;
    }

    private Nutrition addToNutrition(Nutrition nutrition1, Nutrition nutrition2){
        nutrition1.setServingSize(nutrition2.getServingSize());
        nutrition1.setProtein(nutrition2.getProtein());
        nutrition1.setCarbohydrates(nutrition2.getCarbohydrates());
        nutrition1.setFat(nutrition2.getFat());
        nutrition1.setSugar(nutrition2.getSugar());
        nutrition1.setCalories(nutrition2.getCalories());
        return nutrition1;
    }
    private Nutrition nutritionDTOtoNutrition(NutritionDto nutritionDto){
        Nutrition nutrition = new Nutrition();
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
    }
}
