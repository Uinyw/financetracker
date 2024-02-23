package com.example.Analytics.dietFunctionality.logic.model;


import com.example.Analytics.DateConverter;
import com.example.Analytics.dietFunctionality.logic.operations.ProductService;
import org.openapitools.client.ApiException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class Diet {

    private ProductService productService;
    private DateConverter dateConverter;

    public Nutrition getNutritionForDuration(Duration duration) throws ApiException {
        List<Product> productList = productService.getProducts();
        Nutrition nutrition = Nutrition.builder()
                .fat(0.0)
                .calories(0.0)
                .carbohydrates(0.0)
                .sugar(0.0)
                .protein(0.0)
                .servingSize(0.0)
                .build();

        for(Product product: productList){
            Consumption consumption = product.getConsumption();
            if(withinDuration(duration, consumption.getDate()) && product.getNutrition() != null){
                nutrition = addNutritionValues(nutrition, product.getNutrition(), consumption.getAmount(), product.getSize());
            }

        }
        return nutrition;
    }

    private boolean withinDuration(Duration duration, LocalDate date){
        return date.isBefore(duration.getEndTime())
                && date.isAfter(duration.getStartTime());
    }

    private Nutrition addNutritionValues(Nutrition nutrition1, Nutrition nutrition2, double amount, double size){
        nutrition1.setServingSize(nutrition2.getServingSize()*amount*size/100);
        nutrition1.setProtein(nutrition2.getProtein()*amount*size/100);
        nutrition1.setCarbohydrates(nutrition2.getCarbohydrates()*amount*size/100);
        nutrition1.setFat(nutrition2.getFat()*amount*size/100);
        nutrition1.setSugar(nutrition2.getSugar()*amount*size/100);
        nutrition1.setCalories(nutrition2.getCalories()*amount*size/100);
        return nutrition1;
    }
}
