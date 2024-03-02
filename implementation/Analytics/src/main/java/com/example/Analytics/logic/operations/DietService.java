package com.example.Analytics.logic.operations;

import com.example.Analytics.logic.model.productModel.Consumption;
import com.example.Analytics.logic.model.productModel.Duration;
import com.example.Analytics.logic.model.productModel.Nutrition;
import com.example.Analytics.logic.model.productModel.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DietService {

    private final ProductService productService;

    public Nutrition getNutritionForDuration(Duration duration) {
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
        nutrition1.setServingSize(nutrition1.getServingSize()+nutrition2.getServingSize()*amount*size/100.0);
        nutrition1.setProtein(nutrition1.getProtein()+nutrition2.getProtein()*amount*size/100.0);
        nutrition1.setCarbohydrates(nutrition1.getCarbohydrates()+nutrition2.getCarbohydrates()*amount*size/100.0);
        nutrition1.setFat(nutrition1.getFat()+nutrition2.getFat()*amount*size/100.0);
        nutrition1.setSugar(nutrition1.getSugar()+nutrition2.getSugar()*amount*size/100.0);
        nutrition1.setCalories(nutrition1.getCalories()+nutrition2.getCalories()*amount*size/100.0);
        return nutrition1;
    }
}
