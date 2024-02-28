package com.example.Analytics.logic.operations;

import com.example.Analytics.IntegrationTestBase;
import com.example.Analytics.api.mapping.ProductMapper;
import com.example.Analytics.infrastructure.db.ProductRepository;
import com.example.Analytics.logic.model.productModel.Consumption;
import com.example.Analytics.logic.model.productModel.Duration;
import com.example.Analytics.logic.model.productModel.Nutrition;
import com.example.Analytics.logic.model.productModel.Product;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.NutritionDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class DietServiceTest extends IntegrationTestBase {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DietService dietService;

    @Test
    void testProductsWithinDuration() {
        final Consumption consumptionDate1 = getConsumption(LocalDate.now());
        final Consumption consumptionDate2 = getConsumption(LocalDate.now().plusMonths(1));
        final Consumption consumptionDate3 = getConsumption(LocalDate.now().minusMonths(1));

        final Product product1 = createProduct(UUID.randomUUID(), getNutrition(1.0), consumptionDate1);
        final Product product2 = createProduct(UUID.randomUUID(), getNutrition(2.0), consumptionDate2);
        final Product product3 = createProduct(UUID.randomUUID(), getNutrition(3.0), consumptionDate3);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        final Duration twoMonthDuration = new Duration(LocalDate.now().toString(),LocalDate.now().plusMonths(2).toString());

        Nutrition addedNutrition = dietService.getNutritionForDuration(twoMonthDuration);
        //final boolean y = dietService.addNutritionValues(product1.getNutrition(),product2.getNutrition());
        final boolean x = nutritionMatchesValue(addedNutrition,2.0);
        //assertTrue(x);
    }



    private boolean nutritionMatchesValue(Nutrition nutrition, double value){
        return nutrition.getCarbohydrates()==value
                && nutrition.getSugar() == value
                && nutrition.getProtein() == value
                && nutrition.getCalories() == value
                && nutrition.getServingSize() == value
                && nutrition.getFat() == value;
    }

    private NutritionDto getNutritionDto(BigDecimal basevalue){
        return NutritionDto.builder()
                .fat(basevalue)
                .servingSize(basevalue)
                .calories(basevalue)
                .carbohydrates(basevalue)
                .protein(basevalue)
                .sugar(basevalue)
                .build();
    }

    private Product createProduct(){
        return createProduct(UUID.randomUUID(), getNutrition(2.0), getConsumption(LocalDate.now()));
    }
    private Product createProduct(UUID id, Nutrition nutrition, Consumption consumption){
        return Product.builder()
                .id(id)
                .consumption(consumption)
                .name("product Name")
                .size(100.0)
                .nutrition(nutrition)
                .build();
    }

    private Consumption getConsumption(LocalDate date){
        return Consumption.builder()
                .date(LocalDate.now())
                .amount(1.0)
                .build();
    }
    private Nutrition getNutrition(double basevalue){
        return Nutrition.builder()
                .fat(basevalue)
                .servingSize(basevalue)
                .calories(basevalue)
                .carbohydrates(basevalue)
                .protein(basevalue)
                .sugar(basevalue)
                .build();
    }
}
