package com.example.Analytics.logic.api.mapping;

import com.example.Analytics.IntegrationTestBase;
import com.example.Analytics.api.mapping.ProductMapper;
import com.example.Analytics.logic.model.productModel.Nutrition;
import com.example.Analytics.logic.model.productModel.Product;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.CategoryDto;
import org.openapitools.client.model.MonetaryAmountDto;
import org.openapitools.client.model.NutritionDto;
import org.openapitools.client.model.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AnalyticsProductMapperTest extends IntegrationTestBase {
    @Autowired
    private ProductMapper productMapper;

    @Test
    void givenNutritionDto_whenMap_thenProductExists(){
        final NutritionDto nutritionDto = getNutritionDto(BigDecimal.ONE);
        final Nutrition nutrition = productMapper.nutritionFromDTO(nutritionDto);

        double baseValue = 1.0;
        assertThat(nutrition.getFat(), is(baseValue));
        assertThat(nutrition.getServingSize(), is(baseValue));
        assertThat(nutrition.getCalories(), is(baseValue));
        assertThat(nutrition.getCarbohydrates(), is(baseValue));
        assertThat(nutrition.getProtein(), is(baseValue));
        assertThat(nutrition.getSugar(), is(baseValue));

        org.openapitools.model.NutritionDto newNutritionDto = productMapper.nutritionDtoFromNutrition(nutrition);
        assertThat(nutrition.getFat(), is(newNutritionDto.getFat()));
        assertThat(nutrition.getServingSize(), is(newNutritionDto.getServingSize()));
        assertThat(nutrition.getCalories(), is(newNutritionDto.getCalories()));
        assertThat(nutrition.getCarbohydrates(), is(newNutritionDto.getCarbohydrates()));
        assertThat(nutrition.getProtein(), is(newNutritionDto.getProtein()));
        assertThat(nutrition.getSugar(), is(newNutritionDto.getSugar()));
    }


    @Test
    void givenNutritionDto_whenMissingValue_thenProductisNull(){
        NutritionDto invalidNutritionDto = null;

        for (int i = 0; i < 6; i++) {
            System.out.println(i%7);
            invalidNutritionDto = NutritionDto.builder()
                    .fat((i%7 == 0)?null:BigDecimal.ONE)
                    .servingSize((i%7 == 1)?null:BigDecimal.ONE)
                    .calories((i%7 == 2)?null:BigDecimal.ONE)
                    .carbohydrates((i%7 == 3)?null:BigDecimal.ONE)
                    .protein((i%7 == 4)?null:BigDecimal.ONE)
                    .sugar((i%7 == 5)?null:BigDecimal.ONE)
                    .build();
            Nutrition invalidNutrition = productMapper.nutritionFromDTO(invalidNutritionDto);
            assertThat(null, is(invalidNutrition));
        }
    }

    @Test
    void givenProductDto_whenMap_thenProductExists(){
        final UUID uuid = UUID.randomUUID();
        final ProductDto productDto = ProductDto.builder()
                .id(uuid)
                .price(new MonetaryAmountDto(1.0))
                .labels(List.of("fruit", "vegetables"))
                .category(CategoryDto.FOOD)
                .description("test description")
                .name("Product Name")
                .nutrition(getNutritionDto(new BigDecimal(2)))
                .size(new BigDecimal(1))
                .build();

        Product prod = productMapper.productFromDTO(productDto, 1.0);

        assertThat(prod.getName(), is("Product Name"));
        assertThat(prod.getSize(), is(1.0));
        assertThat(prod.getConsumption().getDate(), is(LocalDate.now()));
        assertThat(prod.getConsumption().getAmount(), is(1.0));
        assertThat(prod.getNutrition().getSugar(), is(getNutrition(2.0).getSugar()));
        assertThat(prod.getNutrition().getFat(), is(getNutrition(2.0).getFat()));
        assertThat(prod.getNutrition().getProtein(), is(getNutrition(2.0).getProtein()));
        assertThat(prod.getNutrition().getCalories(), is(getNutrition(2.0).getCalories()));
        assertThat(prod.getNutrition().getCarbohydrates(), is(getNutrition(2.0).getCarbohydrates()));
        assertThat(prod.getNutrition().getServingSize(), is(getNutrition(2.0).getServingSize()));
        assertThat(prod.getId(), is(uuid));

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
}
