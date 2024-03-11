package com.financetracker.product.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.financetracker.product.IntegrationTestBase;
import com.financetracker.product.logic.model.ProductEntryCollectionType;
import io.restassured.http.ContentType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SuppliesResourceTest extends IntegrationTestBase {

    @Value("${tpd.topic-product-update}")
    private String productUpdate;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    private KafkaMessageListenerContainer<String, Object> container;
    private BlockingQueue<ConsumerRecord<String, Object>> consumerRecords;

    @BeforeEach
    void setup() {
        consumerRecords = new LinkedBlockingQueue<>();

        Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps("test-group-id", "false", embeddedKafkaBroker);
        DefaultKafkaConsumerFactory<String, Object> consumer = new DefaultKafkaConsumerFactory<>(consumerProperties);

        ContainerProperties containerProperties = new ContainerProperties(productUpdate);
        container = new KafkaMessageListenerContainer<>(consumer, containerProperties);
        container.setupMessageListener((MessageListener<String, Object>) record -> consumerRecords.add(record));
        container.start();
        ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.getPartitionsPerTopic());
    }

    @AfterEach
    void after() {
        container.stop();
    }

    @Test
    void givenProductEntry_whenAddToSupplies_thenProductEntryExistsInSupplies() {
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
                .desiredQuantity(BigDecimal.TEN)
                .purchased(false)
                .product(productDto)
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(productEntryDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/supplies/entries")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/supplies")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("type", is(ProductEntryCollectionType.SUPPLIES.name()))
                .and().body("productEntries.size()", is(1))
                .and().body("productEntries[0].id", is(productEntryDto.getId().toString()))
                .and().body("productEntries[0].quantity", is(1F))
                .and().body("productEntries[0].desiredQuantity", is(10F))
                .and().body("productEntries[0].purchased", is(false))
                .and().body("productEntries[0].product.id", is(productEntryDto.getProduct().getId().toString()));
    }

    @Test
    void givenProductEntryInSupplies_whenAddProductEntryForSameProduct_thenQuantityAndDesiredQuantityIsReplaced() {
        final var productDto = ProductDto.builder()
                .id(UUID.randomUUID())
                .name("Dog Food")
                .description("Woof")
                .category(CategoryDto.PET_SUPPLIES)
                .price(MonetaryAmountDto.builder().amount(1.49).build())
                .build();

        final var productEntryDto = ProductEntryDto.builder()
                .id(UUID.randomUUID())
                .quantity(BigDecimal.ONE)
                .desiredQuantity(BigDecimal.TEN)
                .purchased(false)
                .product(productDto)
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(productEntryDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/supplies/entries")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/supplies")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("productEntries.size()", is(1))
                .and().body("productEntries[0].id", is(productEntryDto.getId().toString()))
                .and().body("productEntries[0].quantity", is(1F))
                .and().body("productEntries[0].desiredQuantity", is(10F));

        final var newProductEntryDtoForSameProduct = ProductEntryDto.builder()
                .id(UUID.randomUUID())
                .quantity(BigDecimal.TEN)
                .desiredQuantity(new BigDecimal("20"))
                .purchased(false)
                .product(productDto)
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(newProductEntryDtoForSameProduct)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/supplies/entries")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/supplies")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("productEntries.size()", is(1))
                .and().body("productEntries[0].id", is(productEntryDto.getId().toString()))
                .and().body("productEntries[0].quantity", is(10F))
                .and().body("productEntries[0].desiredQuantity", is(20F));
    }

    @Test
    void givenProductEntryInSupplies_whenDeleteFromSupplies_thenProductEntryDoesNotExistInSuppliesAnymore() {
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
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/supplies/entries")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/supplies")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("productEntries.size()", is(1))
                .and().body("productEntries[0].id", is(productEntryDto.getId().toString()));

        given().port(port)
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/supplies/entries/" + productEntryDto.getId().toString())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/supplies")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("productEntries.size()", is(0));
    }

    @Test
    void givenProductEntryInSupplies_whenDeleteEntryByInvalidId_thenNotFound() {
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
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/supplies/entries")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/supplies/entries/" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/supplies")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("productEntries.size()", is(1))
                .and().body("productEntries[0].id", is(productEntryDto.getId().toString()));
    }

    @Test
    void givenProductEntryInSupplies_whenPatchEntryInSupplies_thenProductEntryInSuppliesIsUpdated() throws InterruptedException, JsonProcessingException {
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
                .quantity(BigDecimal.TEN)
                .purchased(false)
                .product(productDto)
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(productEntryDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/supplies/entries")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/supplies")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("productEntries.size()", is(1))
                .and().body("productEntries[0].id", is(productEntryDto.getId().toString()))
                .and().body("productEntries[0].quantity", is(10F));

        productEntryDto.setQuantity(BigDecimal.ONE);

        given().port(port)
                .contentType(ContentType.JSON)
                .body(productEntryDto)
                .patch(LOCAL_BASE_URL_WITHOUT_PORT + "/supplies/entries/" + productEntryDto.getId().toString())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        final var consumedRecord = consumerRecords.poll(1, TimeUnit.SECONDS);
        assertNotNull(consumedRecord);

        final var objectMapper = new ObjectMapper();
        final var record = objectMapper.readTree(consumedRecord.value().toString());
        assertThat(record.get("amount").asDouble(), is(9.0));
        assertThat(record.get("product").get("id").asText(), is(productDto.getId().toString()));

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/supplies")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("productEntries.size()", is(1))
                .and().body("productEntries[0].id", is(productEntryDto.getId().toString()))
                .and().body("productEntries[0].quantity", is(1F));
    }

    @Test
    void givenProductEntryInSupplies_whenPatchEntryByInvalidId_thenNotFound() {
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
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/supplies/entries")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        productEntryDto.setQuantity(BigDecimal.TEN);

        given().port(port)
                .contentType(ContentType.JSON)
                .body(productEntryDto)
                .patch(LOCAL_BASE_URL_WITHOUT_PORT + "/supplies/entries/" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/supplies")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("productEntries.size()", is(1))
                .and().body("productEntries[0].id", is(productEntryDto.getId().toString()))
                .and().body("productEntries[0].quantity", is(1F));
    }

    @Test
    void givenProductInSuppliesWithDesiredQuantityHigherThanQuantity_whenShopNeededSupplies_thenProductIsAddedToShoppingCart() {
        final var productDto = ProductDto.builder()
                .id(UUID.randomUUID())
                .name("GlassCleaner")
                .description("Keep the bath clean.")
                .category(CategoryDto.HOUSEHOLD)
                .price(MonetaryAmountDto.builder().amount(2.39).build())
                .labels(List.of("EssentialProduct"))
                .build();

        final var productEntryDto = ProductEntryDto.builder()
                .id(UUID.randomUUID())
                .quantity(BigDecimal.ZERO)
                .desiredQuantity(BigDecimal.ONE)
                .purchased(false)
                .product(productDto)
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(productEntryDto)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/supplies/entries")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("productEntries.size()", is(0));

        given().port(port)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/supplies/shop")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/shopping-cart")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("productEntries.size()", is(1))
                .and().body("productEntries[0].quantity", is(1F))
                .and().body("productEntries[0].product.id", is(productDto.getId().toString()));
    }
}
