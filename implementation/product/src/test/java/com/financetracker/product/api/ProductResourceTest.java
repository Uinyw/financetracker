package com.financetracker.product.api;

import com.financetracker.product.IntegrationTestBase;
import com.financetracker.product.infrastructure.nutrition.NutritionProvider;
import com.financetracker.product.logic.model.Nutrition;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.model.CategoryDto;
import org.openapitools.model.MonetaryAmountDto;
import org.openapitools.model.ProductDto;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ProductResourceTest extends IntegrationTestBase {

    @MockBean
    private NutritionProvider nutritionProvider;

    @BeforeEach
    void setUp() {
        final var nutrition = new Nutrition(new BigDecimal("100"), new BigDecimal("30"), new BigDecimal("60"), new BigDecimal("5"), new BigDecimal("2"), new BigDecimal("10"));
        doReturn(nutrition).when(nutritionProvider).getNutritionForProduct(anyString());
    }

    @Test
    void givenProduct_whenCreateProduct_thenProductExistsInSystem() {
        final var productDto = ProductDto.builder()
                .id(UUID.randomUUID())
                .name("Milk")
                .description("Tasty Drink")
                .category(CategoryDto.DRINKS)
                .price(MonetaryAmountDto.builder().amount(1.49).build())
                .labels(List.of("EssentialProduct"))
                .build();

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/products")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(0));

        given().port(port)
                .contentType(ContentType.JSON)
                .body(productDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/products")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/products")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(1))
                .and().body("[0].id", is(productDto.getId().toString()))
                .and().body("[0].name", is(productDto.getName()))
                .and().body("[0].description", is(productDto.getDescription()))
                .and().body("[0].category", is(productDto.getCategory().toString()))
                .and().body("[0].nutrition.servingSize", is(100F))
                .and().body("[0].nutrition.calories", is(30F))
                .and().body("[0].nutrition.carbohydrates", is(60F))
                .and().body("[0].nutrition.protein", is(5F))
                .and().body("[0].nutrition.fat", is(2F))
                .and().body("[0].nutrition.sugar", is(10F))
                .and().body("[0].labels.size()", is(1))
                .and().body("[0].labels[0]", is("EssentialProduct"));

        verify(nutritionProvider, times(1)).getNutritionForProduct(anyString());
    }

    @Test
    void givenExistingProduct_whenGetProductById_thenProductIsReturned() {
        final var productDto = ProductDto.builder()
                .id(UUID.randomUUID())
                .name("Milk")
                .description("Tasty Drink")
                .category(CategoryDto.DRINKS)
                .price(MonetaryAmountDto.builder().amount(1.49).build())
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(productDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/products")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/products/" + productDto.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("id", is(productDto.getId().toString()))
                .and().body("name", is(productDto.getName()))
                .and().body("description", is(productDto.getDescription()))
                .and().body("category", is(productDto.getCategory().toString()))
                .and().body("price.amount", is(1.49F))
                .and().body("labels.size()", is(0))
                .and().body("nutrition.servingSize", is(100F))
                .and().body("nutrition.calories", is(30F))
                .and().body("nutrition.carbohydrates", is(60F))
                .and().body("nutrition.protein", is(5F))
                .and().body("nutrition.fat", is(2F))
                .and().body("nutrition.sugar", is(10F));
    }

    @Test
    void givenExistingProduct_whenDeleteProductById_thenProductIsRemovedFromSystem() {
        final var productDto = ProductDto.builder()
                .id(UUID.randomUUID())
                .name("Milk")
                .description("Tasty Drink")
                .category(CategoryDto.DRINKS)
                .price(MonetaryAmountDto.builder().amount(1.49).build())
                .labels(List.of("EssentialProduct"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(productDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/products")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/products/" + productDto.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("id", is(productDto.getId().toString()));

        given().port(port)
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/products/" + productDto.getId().toString())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/products/" + productDto.getId().toString())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/products")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(0));
    }

    @Test
    void givenExistingProduct_whenPatchProductById_thenProductIsUpdated() {
        final var productDto = ProductDto.builder()
                .id(UUID.randomUUID())
                .name("Milk")
                .description("Tasty Drink")
                .category(CategoryDto.DRINKS)
                .price(MonetaryAmountDto.builder().amount(1.49).build())
                .labels(List.of("EssentialProduct"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(productDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/products")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/products/" + productDto.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("id", is(productDto.getId().toString()))
                .and().body("name", is("Milk"));

        productDto.setName("New Milk");

        given().port(port)
                .contentType(ContentType.JSON)
                .body(productDto)
                .patch(LOCAL_BASE_URL_WITHOUT_PORT + "/products/" + productDto.getId().toString())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/products/" + productDto.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("id", is(productDto.getId().toString()))
                .and().body("name", is("New Milk"));
    }
}
