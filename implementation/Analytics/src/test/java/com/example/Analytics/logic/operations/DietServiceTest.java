package com.example.Analytics.logic.operations;

import com.example.Analytics.IntegrationTestBase;
import com.example.Analytics.infrastructure.db.ProductRepository;
import com.example.Analytics.logic.model.productModel.Consumption;
import com.example.Analytics.logic.model.productModel.Duration;
import com.example.Analytics.logic.model.productModel.Nutrition;
import com.example.Analytics.logic.model.productModel.Product;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class DietServiceTest extends IntegrationTestBase {
        @InjectMocks
        private DietService dietService;

        @Mock
        private ProductService productService;

        @Mock
        private ProductRepository productRepository;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.initMocks(this);
        }


        @Test
        public void givenDuration_whenCalled_thenNoValueFound() {
            Duration twoMonthDuration = new Duration(LocalDate.now().plusMonths(3).toString(),LocalDate.now().plusMonths(5).plusMonths(2).toString());
            double value = 0.0;
            Nutrition result = dietService.getNutritionForDuration(twoMonthDuration);

            assertThat(result.getCarbohydrates(), is(value));
            assertThat(result.getSugar(), is(value));
            assertThat(result.getProtein(), is(value));
            assertThat(result.getCalories(), is(value));
            assertThat(result.getServingSize(), is(value));
            assertThat(result.getFat(), is(value));
        }

        @Test
        void givenDuration_whenCalled_thenRightValueFound(){
            Duration twoMonthDuration = new Duration(LocalDate.now().minusDays(1).toString(),LocalDate.now().plusMonths(2).toString());
            double value = 3.0;

            final LocalDate localDate1 = LocalDate.now();
            final LocalDate localDate2 = localDate1.plusMonths(1);
            final LocalDate localDate3 = localDate1.minusMonths(1);

            final Consumption consumptionDate1 = getConsumption(localDate1);
            final Consumption consumptionDate2 = getConsumption(localDate2);
            final Consumption consumptionDate3 = getConsumption(localDate3);

            final Product product1 = createProduct(UUID.randomUUID(), createNutrition(1.0), consumptionDate1);
            final Product product2 = createProduct(UUID.randomUUID(), createNutrition(2.0), consumptionDate2);
            final Product product3 = createProduct(UUID.randomUUID(), createNutrition(3.0), consumptionDate3);

            when(productRepository.findAll()).thenReturn(List.of(product1, product2, product3));
            when(productService.getProducts()).thenReturn(List.of(product1, product2, product3));
            ReflectionTestUtils.setField(productService, "productRepository", productRepository);
            ReflectionTestUtils.setField(dietService, "productService", productService);

            Nutrition result = dietService.getNutritionForDuration(twoMonthDuration);

            assertThat(result.getCarbohydrates(), is(value));
            assertThat(result.getSugar(), is(value));
            assertThat(result.getProtein(), is(value));
            assertThat(result.getCalories(), is(value));
            assertThat(result.getServingSize(), is(value));
            assertThat(result.getFat(), is(value));
        }

        @Test
        void givenNutritionValues_whenAdded_thenCombinedValue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            final Nutrition nutrition1 = createNutrition(1.0);
            final Nutrition nutrition2 = createNutrition(2.0);

            Method addNutritionValuesMethod = DietService.class.getDeclaredMethod("addNutritionValues", Nutrition.class, Nutrition.class, double.class, double.class);
            addNutritionValuesMethod.setAccessible(true);

            Nutrition nutrition = (Nutrition) addNutritionValuesMethod.invoke(dietService, nutrition1, nutrition2, 1.0, 100.0);
            double x = 3.0;

            assertThat(nutrition.getCarbohydrates(), is(x));
            assertThat(nutrition.getSugar(), is(x));
            assertThat(nutrition.getProtein(), is(x));
            assertThat(nutrition.getCalories(), is(x));
            assertThat(nutrition.getServingSize(), is(x));
            assertThat(nutrition.getFat(), is(x));
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
                .date(date)
                .amount(1.0)
                .build();
    }
    private Nutrition createNutrition(double basevalue){
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
