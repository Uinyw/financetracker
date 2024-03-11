package com.example.Analytics.logic.model;

import com.example.Analytics.IntegrationTestBase;
import com.example.Analytics.logic.model.productModel.Consumption;
import com.example.Analytics.logic.model.productModel.Nutrition;
import com.example.Analytics.logic.model.productModel.Product;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductModelsTest  extends IntegrationTestBase {

    @Test
    void givenConsumption_whenSetAttributes_thenAttributesAreUpdated(){
        final Consumption basicConsumption = Consumption.builder()
                .date(LocalDate.now())
                .amount(1.2)
                .build();

        basicConsumption.setAmount(2.0);
        basicConsumption.setDate(LocalDate.now().plusDays(1));

        assertThat(basicConsumption.getDate(), is(LocalDate.now().plusDays(1)));
        assertThat(basicConsumption.getAmount(), is(2.0));
    }
    @Test
    void givenNutrition_whenSetAttributes_thenAttributesAreUpdated(){
        double baseValue = 2.0;
        final Nutrition nutrition = Nutrition.builder()
                .fat(baseValue)
                .servingSize(baseValue)
                .calories(baseValue)
                .carbohydrates(baseValue)
                .protein(baseValue)
                .sugar(baseValue)
                .build();

        baseValue = 1.0;
        nutrition.setFat(baseValue);
        nutrition.setServingSize(baseValue);
        nutrition.setCalories(baseValue);
        nutrition.setCarbohydrates(baseValue);
        nutrition.setProtein(baseValue);
        nutrition.setSugar(baseValue);

        assertThat(nutrition.getFat(), is(baseValue));
        assertThat(nutrition.getServingSize(), is(baseValue));
        assertThat(nutrition.getCalories(), is(baseValue));
        assertThat(nutrition.getCarbohydrates(), is(baseValue));
        assertThat(nutrition.getProtein(), is(baseValue));
        assertThat(nutrition.getSugar(), is(baseValue));

    }
    @Test
    void givenProduct_whenSetAttributes_thenAttributesAreUpdated(){
        final Product product = Product.builder()
                .id(UUID.randomUUID())
                .name("basic Product")
                .size(1.0)
                .consumption(Consumption.builder().build())
                .nutrition(Nutrition.builder().build())
                .build();

        UUID randomId = UUID.randomUUID();
        Consumption newConsumption = new Consumption(LocalDate.now(), 20);
        Nutrition newNutrition = new Nutrition(1.0,1.0,1.0,1.0,1.0,1.0);

        product.setId(randomId);
        product.setName("configured Product");
        product.setSize(2.0);
        product.setConsumption(newConsumption);
        product.setNutrition(newNutrition);

        assertThat(product.getId(), is(randomId));
        assertThat(product.getName(), is("configured Product"));
        assertThat(product.getSize(), is(2.0));
        assertThat(product.getConsumption(), is(newConsumption));
        assertThat(product.getNutrition(), is(newNutrition));
    }

    @Test
    void givenNutritionBuilder_whenToString_thenStringRepresentationContainsValidId() {
        final var baseValue = 2.0;
        final String nutrition = Nutrition.builder()
                .fat(baseValue)
                .servingSize(baseValue)
                .calories(baseValue)
                .carbohydrates(baseValue)
                .protein(baseValue)
                .sugar(baseValue)
                .build()
                .toString();

        //assertTrue(nutrition.contains("fat=" + baseValue));
    }
    @Test
    void givenProductBuilder_whenToString_thenStringRepresentationContainsValidId() {
        final var id = UUID.randomUUID();
        final var product = Product.builder()
                .id(id)
                .name("basic Product")
                .size(1.0)
                .consumption(Consumption.builder().build())
                .nutrition(Nutrition.builder().build())
                .build()
                .toString();
        //assertTrue(product.contains("id=" + id));
    }

    @Test
    void givenConsumptionBuilder_whenToString_thenStringRepresentationContainsValidId() {
        final var date = LocalDate.now();
        final String basicConsumption = Consumption.builder()
                .date(date)
                .amount(1.2)
                .build()
                .toString();

        //assertTrue(basicConsumption.contains("id=" + date.toString()));
    }
}
