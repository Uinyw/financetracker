package com.financetracker.product.api;

import com.financetracker.product.IntegrationTestBase;
import com.financetracker.product.infrastructure.client.TransactionProvider;
import com.financetracker.product.logic.model.MonetaryAmount;
import com.financetracker.product.logic.model.ProductEntryCollectionType;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.model.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ShoppingCartResourceTest extends IntegrationTestBase {

    @MockBean
    private TransactionProvider transactionProvider;

    @BeforeEach
    void setUp() {
        doNothing().when(transactionProvider).bookExpense(any(UUID.class), any(MonetaryAmount.class));
    }

    @Test
    void givenProductEntry_whenAddToShoppingCart_thenProductEntryExistsInShoppingCart() {
        final var productDto = ProductDto.builder()
                .id(UUID.randomUUID())
                .name("Pasta")
                .description("Important for every Student")
                .category(CategoryDto.FOOD)
                .price(MonetaryAmountDto.builder().amount(1.49).build())
                .build();

        final var productEntryDto = ProductEntryDto.builder()
                .id(UUID.randomUUID())
                .quantity(BigDecimal.ONE)
                .purchased(false)
                .product(productDto)
                .build();

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

    @Test
    void givenProductEntryInShoppingCart_whenAddProductEntryForSameProduct_thenQuantityIsReplaced() {
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
                .body(productEntryDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart/entries")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("productEntries.size()", is(1))
                .and().body("productEntries[0].id", is(productEntryDto.getId().toString()))
                .and().body("productEntries[0].quantity", is(1F));

        final var newProductEntryDtoForSameProduct = ProductEntryDto.builder()
                .id(UUID.randomUUID())
                .quantity(BigDecimal.TEN)
                .purchased(false)
                .product(productDto)
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(newProductEntryDtoForSameProduct)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart/entries")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("productEntries.size()", is(1))
                .and().body("productEntries[0].id", is(productEntryDto.getId().toString()))
                .and().body("productEntries[0].quantity", is(10F));
    }

    @Test
    void givenProductEntryInShoppingCart_whenDeleteFromShoppingCart_thenProductEntryDoesNotExistInShoppingCartAnymore() {
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
                .body(productEntryDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart/entries")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("productEntries.size()", is(1))
                .and().body("productEntries[0].id", is(productEntryDto.getId().toString()));

        given().port(port)
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart/entries/" + productEntryDto.getId().toString())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("productEntries.size()", is(0));
    }

    @Test
    void givenProductEntryInShoppingCart_whenDeleteEntryByInvalidId_thenNotFound() {
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
                .body(productEntryDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart/entries")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(productEntryDto)
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart/entries/" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("productEntries.size()", is(1))
                .and().body("productEntries[0].id", is(productEntryDto.getId().toString()));
    }

    @Test
    void givenProductEntryInShoppingCart_whenPatchEntryInShoppingCart_thenProductEntryInShoppingCartIsUpdated() {
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
                .body(productEntryDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart/entries")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("productEntries.size()", is(1))
                .and().body("productEntries[0].id", is(productEntryDto.getId().toString()))
                .and().body("productEntries[0].quantity", is(1F));

        productEntryDto.setQuantity(BigDecimal.TEN);

        given().port(port)
                .contentType(ContentType.JSON)
                .body(productEntryDto)
                .patch(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart/entries/" + productEntryDto.getId().toString())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("productEntries.size()", is(1))
                .and().body("productEntries[0].id", is(productEntryDto.getId().toString()))
                .and().body("productEntries[0].quantity", is(10F));
    }

    @Test
    void givenProductEntryInShoppingCart_whenPatchEntryByInvalidId_thenNotFound() {
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
                .body(productEntryDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart/entries")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        productEntryDto.setQuantity(BigDecimal.TEN);

        given().port(port)
                .contentType(ContentType.JSON)
                .body(productEntryDto)
                .patch(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart/entries/" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("productEntries.size()", is(1))
                .and().body("productEntries[0].id", is(productEntryDto.getId().toString()))
                .and().body("productEntries[0].quantity", is(1F));
    }

    @Test
    void givenProductEntryInShoppingCart_whenMarkProductPurchased_thenProductIsSetToPurchased() {
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
                .body(productEntryDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart/entries")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("productEntries.size()", is(1))
                .and().body("productEntries[0].id", is(productEntryDto.getId().toString()))
                .and().body("productEntries[0].purchased", is(false));

        given().port(port)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart/entries/" + productEntryDto.getId().toString() + "/mark")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("productEntries.size()", is(1))
                .and().body("productEntries[0].id", is(productEntryDto.getId().toString()))
                .and().body("productEntries[0].purchased", is(true));
    }

    @Test
    void givenPurchasedProductEntryInShoppingCart_whenPurchaseShoppingCart_thenTransactionIsCreated() {
        final var productDto = ProductDto.builder()
                .id(UUID.randomUUID())
                .name("Milk")
                .description("Tasty Drink")
                .category(CategoryDto.DRINKS)
                .price(MonetaryAmountDto.builder().amount(5.0).build())
                .labels(List.of("EssentialProduct"))
                .build();

        final var productEntryDto = ProductEntryDto.builder()
                .id(UUID.randomUUID())
                .quantity(new BigDecimal("2"))
                .purchased(false)
                .product(productDto)
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(productEntryDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart/entries")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart/entries/" + productEntryDto.getId().toString() + "/mark")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        final var purchaseDto = PurchaseDto.builder()
                .sourceBankAccountId(UUID.randomUUID())
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(purchaseDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart/purchase")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("type", is(ProductEntryCollectionType.SHOPPING_CART.name()))
                .and().body("productEntries.size()", is(0));

        verify(transactionProvider, times(1)).bookExpense(purchaseDto.getSourceBankAccountId(), new MonetaryAmount(BigDecimal.TEN));
    }
}
