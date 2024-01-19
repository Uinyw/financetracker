package com.financetracker.product.infrastructure.nutrition.mapping;

import com.financetracker.product.infrastructure.nutrition.model.NutritionInformation;
import com.financetracker.product.logic.model.Nutrition;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class NutritionMapper {

    public Nutrition mapNutritionInformationToNutritionModel(final NutritionInformation nutritionInformation) {
        return new Nutrition(BigDecimal.valueOf(nutritionInformation.items().get(0).serving_size_g()),
                BigDecimal.valueOf(nutritionInformation.items().get(0).calories()),
                BigDecimal.valueOf(nutritionInformation.items().get(0).carbohydrates_total_g()),
                BigDecimal.valueOf(nutritionInformation.items().get(0).protein_g()),
                BigDecimal.valueOf(nutritionInformation.items().get(0).fat_total_g()),
                BigDecimal.valueOf(nutritionInformation.items().get(0).sugar_g()));
    }
}
