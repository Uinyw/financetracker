package com.example.Analytics.logic.operations;

import com.example.Analytics.IntegrationTestBase;
import com.example.Analytics.api.mapping.ProductMapper;
import com.example.Analytics.infrastructure.db.ProductRepository;
import com.example.Analytics.infrastructure.kafka.config.UpdateType;
import com.example.Analytics.logic.model.productModel.Consumption;
import com.example.Analytics.logic.model.productModel.Nutrition;
import com.example.Analytics.logic.model.productModel.Product;
import jakarta.persistence.Embedded;
import org.junit.jupiter.api.AfterEach;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductServiceTest extends IntegrationTestBase {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testProductChange_whenChanged_correctlyChanged() {
        final Product product= createProduct();

        productService.createProduct(product);

        final var x = productRepository.findAll();
        assertEquals(1, x.size());
        }
    @Test
    void testProductUpdate_whenChanged_correctlyUpdated(){
        final UUID id = UUID.randomUUID();
        final LocalDate consumptionDate = LocalDate.now();
        final var product = createProduct(id, getNutrition(1.0), getConsumption(consumptionDate));
        final var updatedProduct = createProduct(id, getNutrition(2.0), getConsumption(consumptionDate));

        productService.createProduct(product);
        productService.createProduct(updatedProduct);

        final var x = productRepository.findAll();
        assertEquals(1, x.size());
    }
    @Test
    void testProductReceiveDtoCreate_whenCreated_thenExists() {
        final ProductDto productDto = ProductDto.builder()
                .id(UUID.randomUUID())
                .price(new MonetaryAmountDto(1.0))
                .labels(List.of("fruit", "vegetables"))
                .category(CategoryDto.FOOD)
                .description("test description")
                .name("Product name")
                .nutrition(getNutritionDto(new BigDecimal(2)))
                .size(new BigDecimal(1))
                .build();

        productService.receiveProduct(productDto, 2.0);

        final var x = productRepository.findAll();
        assertEquals(1, x.size());
    }
    @Test
    void getProdcut_whenExists_thenReceive(){
        final UUID id = UUID.randomUUID();
        final ProductDto productDto = ProductDto.builder()
                .id(id)
                .price(new MonetaryAmountDto(1.0))
                .labels(List.of("fruit", "vegetables"))
                .category(CategoryDto.FOOD)
                .description("test description")
                .name("Product name")
                .nutrition(getNutritionDto(new BigDecimal(2)))
                .size(new BigDecimal(1))
                .build();

        productService.receiveProduct(productDto, 2.0);

        final var x = productService.getProduct(id.toString());
        assertTrue(x.isPresent());
    }
    @Test
    void testProductReceivePoductDto_whenCreated_thenExists(){
        final ProductDto productDto = ProductDto.builder()
                .id(UUID.randomUUID())
                .price(new MonetaryAmountDto(1.0))
                .labels(List.of("fruit", "vegetables"))
                .category(CategoryDto.FOOD)
                .description("test description")
                .name("Product name")
                .nutrition(getNutritionDto(new BigDecimal(2)))
                .size(new BigDecimal(1))
                .build();

        productService.receiveProduct(productDto, 2.0);

        final var x = productService.getProducts();
        assertEquals(1, x.size());
    }
    @Test
    void testProductGetPoducts_whenCreated_thenReceive(){
        final Product product = createProduct();

        productService.createProduct(product);

        final var x = productService.getProducts();
        assertEquals(1, x.size());
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
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
                .size(1.0)
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
