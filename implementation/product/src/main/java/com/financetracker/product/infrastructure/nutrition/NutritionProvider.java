package com.financetracker.product.infrastructure.nutrition;

import com.financetracker.product.logic.model.Nutrition;

public interface NutritionProvider {

    Nutrition getNutritionForProduct(final String productName);

}
