package com.example.Analytics.DietFunctionality;

import com.example.Analytics.BudgetFunctionality.DateConverter;
import com.example.Analytics.DietFunctionality.DietMapper.ProductService;
import org.openapitools.client.ApiException;
import org.openapitools.client.api.ProductApi;
import org.openapitools.client.model.NutritionDto;
import org.openapitools.client.model.ProductDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class Diet {

    private ProductApi productApi;
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
                nutrition = addToNutrition(nutrition, product.getNutrition(), consumption.getAmount());
            }

        }
        return nutrition;
    }

    private boolean withinDuration(Duration duration, Date date){
        return dateConverter.dateToLocalDate(date).isBefore(duration.getEndTime())
                && dateConverter.dateToLocalDate(date).isAfter(duration.getStartTime());
    }

    private Nutrition addToNutrition(Nutrition nutrition1, Nutrition nutrition2, int amount){
        //TODO amount of products
        nutrition1.setServingSize(nutrition2.getServingSize()*amount);
        nutrition1.setProtein(nutrition2.getProtein()*amount);
        nutrition1.setCarbohydrates(nutrition2.getCarbohydrates()*amount);
        nutrition1.setFat(nutrition2.getFat()*amount);
        nutrition1.setSugar(nutrition2.getSugar()*amount);
        nutrition1.setCalories(nutrition2.getCalories()*amount);
        return nutrition1;
    }
}
