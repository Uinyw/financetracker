package com.financetracker.product.api;

import com.financetracker.product.IntegrationTestBase;
import com.financetracker.product.logic.model.ProductEntryCollectionType;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.openapitools.model.CategoryDto;
import org.openapitools.model.MonetaryAmountDto;
import org.openapitools.model.ProductDto;
import org.openapitools.model.ProductEntryDto;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

class ShoppingCartResourceTest extends IntegrationTestBase {

    @Test
    void givenProductEntry_whenAddToShoppingCart_thenProductEntryExistsInShoppingCart() {
        final var productDto = ProductDto.builder()
                .id(UUID.randomUUID())
                .name("Milk")
                .description("Tasty Drink")
                .category(CategoryDto.DRINKS)
                .price(MonetaryAmountDto.builder().amount(1.49).build())
                .labels(List.of("EssentialProduct"))
                .build();

        final var productEntryDto = ProductEntryDto.builder()
                .id(UUID.randomUUID())
                .quantity(BigDecimal.ONE)
                .purchased(false)
                .product(productDto)
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(productDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/products")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(productEntryDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart/entries")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("type", is(ProductEntryCollectionType.SHOPPING_CART.name()))
                .and().body("productEntries.size()", is(1))
                .and().body("productEntries[0].id", is(productEntryDto.getId().toString()))
                .and().body("productEntries[0].quantity", is(1F))
                .and().body("productEntries[0].purchased", is(false))
                .and().body("productEntries[0].product.id", is(productEntryDto.getProduct().getId().toString()));
    }
}
