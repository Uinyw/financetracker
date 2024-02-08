package com.financetracker.product.infrastructure.nutrition;

import com.financetracker.product.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class CalorieNinjasTest extends IntegrationTestBase {

    @Autowired
    private CalorieNinjas calorieNinjas;

    @Test
    void givenValidProductName_whenGetNutritionForProduct_thenValidNutritionIsReturned() {
        final var nutrition = calorieNinjas.getNutritionForProduct("Pasta");

        assertNotNull(nutrition);
        assertNotNull(nutrition.servingSize());
        assertNotNull(nutrition.calories());
        assertNotNull(nutrition.carbohydrates());
        assertNotNull(nutrition.sugar());
        assertNotNull(nutrition.fat());
        assertNotNull(nutrition.protein());
    }

    @Test
    void givenInvalidValidProductName_whenGetNutritionForProduct_thenValidNutritionIsReturned() {
        final var nutrition = calorieNinjas.getNutritionForProduct("foo");

        assertNull(nutrition);
    }
}
