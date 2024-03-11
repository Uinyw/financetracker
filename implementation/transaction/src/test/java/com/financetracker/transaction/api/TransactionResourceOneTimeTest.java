package com.financetracker.transaction.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.financetracker.transaction.IntegrationTestBase;
import io.restassured.http.ContentType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.BankAccountDto;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

class TransactionResourceOneTimeTest extends IntegrationTestBase {

    @Value("${tpd.topic-oneTimeTransaction-update}")
    private String oneTimeTransactionUpdate;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    private KafkaMessageListenerContainer<String, Object> container;
    private BlockingQueue<ConsumerRecord<String, Object>> consumerRecords;

    @BeforeEach
    void setup() {
        consumerRecords = new LinkedBlockingQueue<>();

        Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps("test-group-id", "false", embeddedKafkaBroker);
        DefaultKafkaConsumerFactory<String, Object> consumer = new DefaultKafkaConsumerFactory<>(consumerProperties);

        ContainerProperties containerProperties = new ContainerProperties(oneTimeTransactionUpdate);
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
    void givenOneTimeTransactionAndBankAccountExists_whenCreateOneTimeTransaction_thenOneTimeTransactionExistsInSystem() throws InterruptedException, JsonProcessingException {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var oneTimeTransaction = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("OneTime Payment")
                .description("For pizza last evening.")
                .date(LocalDate.now().toString())
                .type(TypeDto.EXPENSE)
                .amount(MonetaryAmountDto.builder().amount(14.99).build())
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("Thomas BankAccount").build())
                .labels(List.of("Friend"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(1))
                .and().body("[0].id", is(oneTimeTransaction.getId().toString()))
                .and().body("[0].name", is(oneTimeTransaction.getName()))
                .and().body("[0].description", is(oneTimeTransaction.getDescription()))
                .and().body("[0].date", is(oneTimeTransaction.getDate()))
                .and().body("[0].type", is(oneTimeTransaction.getType().toString()))
                .and().body("[0].transferStatus", is(TransferStatusDto.INITIAL.toString()))
                .and().body("[0].amount.amount", is(14.99F))
                .and().body("[0].transfer.sourceBankAccountId", is(oneTimeTransaction.getTransfer().getSourceBankAccountId().toString()))
                .and().body("[0].transfer.externalSourceId", is(nullValue()))
                .and().body("[0].transfer.targetBankAccountId", is(nullValue()))
                .and().body("[0].transfer.externalTargetId", is(oneTimeTransaction.getTransfer().getExternalTargetId()))
                .and().body("[0].labels.size()", is(1))
                .and().body("[0].labels[0]", is("Friend"));

        final var consumedRecord = consumerRecords.poll(1, TimeUnit.SECONDS);
        assertNotNull(consumedRecord);

        final var objectMapper = new ObjectMapper();
        final var record = objectMapper.readTree(consumedRecord.value().toString());
        assertThat(record.get("updateType").asText(), is("CREATE"));
        assertThat(record.get("oneTimeTransaction").get("id").asText(), is(oneTimeTransaction.getId().toString()));
    }

    @Test
    void givenOneTimeTransactionAndBankAccountDoesNotExist_whenCreateOneTimeTransaction_thenBadRequest() {
        doReturn(Optional.empty()).when(bankAccountProvider).getBankAccount(anyString());

        final var oneTimeTransaction = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("OneTime Payment")
                .description("For pizza last evening.")
                .date(LocalDate.now().toString())
                .type(TypeDto.EXPENSE)
                .amount(MonetaryAmountDto.builder().amount(14.99).build())
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("Thomas BankAccount").build())
                .labels(List.of("Friend"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(0));
    }

    @Test
    void givenExistingOneTimeTransaction_whenGetTransactionByTargetBankAccount_thenTransactionIsReturned() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var oneTimeTransaction = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("OneTime Income")
                .description("For pizza last evening.")
                .date(LocalDate.now().toString())
                .type(TypeDto.INCOME)
                .amount(MonetaryAmountDto.builder().amount(14.99).build())
                .transfer(TransferDto.builder().externalSourceId("Marks BankAccount").targetBankAccountId(UUID.randomUUID()).build())
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime?targetBankAccount=" + oneTimeTransaction.getTransfer().getTargetBankAccountId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(1))
                .and().body("[0].id", is(oneTimeTransaction.getId().toString()));
    }

    @Test
    void givenExistingOneTimeTransaction_whenGetTransactionBySourceBankAccount_thenTransactionIsReturned() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var oneTimeTransaction = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("OneTime Payment")
                .description("For pizza last evening.")
                .date(LocalDate.now().toString())
                .type(TypeDto.EXPENSE)
                .amount(MonetaryAmountDto.builder().amount(14.99).build())
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("Thomas BankAccount").build())
                .labels(List.of("Friend"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime?sourceBankAccount=" + oneTimeTransaction.getTransfer().getSourceBankAccountId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(1))
                .and().body("[0].id", is(oneTimeTransaction.getId().toString()));
    }

    @Test
    void givenExistingOneTimeTransaction_whenGetTransactionById_thenTransactionIsReturned() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var oneTimeTransaction = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("OneTime Income")
                .description("For pizza last evening.")
                .date(LocalDate.now().toString())
                .type(TypeDto.INCOME)
                .amount(MonetaryAmountDto.builder().amount(14.99).build())
                .transfer(TransferDto.builder().externalSourceId("Marks BankAccount").targetBankAccountId(UUID.randomUUID()).build())
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("id", is(oneTimeTransaction.getId().toString()));
    }

    @Test
    void givenExistingOneTimeTransaction_whenGetTransactionByInvalidId_thenNotFound() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var oneTimeTransaction = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Savings Shift")
                .description("I want to save some money.")
                .date(LocalDate.now().toString())
                .type(TypeDto.SHIFT)
                .amount(MonetaryAmountDto.builder().amount(200.0).build())
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).targetBankAccountId(UUID.randomUUID()).build())
                .labels(List.of("SavingsGoal"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void givenExistingOneTimeTransaction_whenPatchTransaction_thenTransactionIsUpdated() throws InterruptedException, JsonProcessingException {
        final var objectMapper = new ObjectMapper();

        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var oneTimeTransaction = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("OneTime Payment")
                .description("For pizza last evening.")
                .date(LocalDate.now().toString())
                .type(TypeDto.EXPENSE)
                .amount(MonetaryAmountDto.builder().amount(14.99).build())
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("Thomas BankAccount").build())
                .labels(List.of("Friend"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        final var consumerRecordAfterCreate = consumerRecords.poll(1, TimeUnit.SECONDS);
        assertNotNull(consumerRecordAfterCreate);
        final var recordAfterCreate = objectMapper.readTree(consumerRecordAfterCreate.value().toString());
        assertThat(recordAfterCreate.get("updateType").asText(), is("CREATE"));

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("name", is("OneTime Payment"));

        oneTimeTransaction.setName("New OneTime Payment");

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .patch(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("name", is("New OneTime Payment"));

        final var consumedRecordAfterPatch = consumerRecords.poll(1, TimeUnit.SECONDS);
        assertNotNull(consumedRecordAfterPatch);

        final var recordAfterPatch = objectMapper.readTree(consumedRecordAfterPatch.value().toString());
        assertThat(recordAfterPatch.get("updateType").asText(), is("UPDATE"));
        assertThat(recordAfterPatch.get("oneTimeTransaction").get("id").asText(), is(oneTimeTransaction.getId().toString()));
        assertThat(recordAfterPatch.get("oneTimeTransaction").get("name").asText(), is("New OneTime Payment"));
    }

    @Test
    void givenExistingOneTimeTransaction_whenPatchTransactionByInvalidId_thenNotFound() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var oneTimeTransaction = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("OneTime Payment")
                .description("For pizza last evening.")
                .date(LocalDate.now().toString())
                .type(TypeDto.EXPENSE)
                .amount(MonetaryAmountDto.builder().amount(14.99).build())
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("Thomas BankAccount").build())
                .labels(List.of("Friend"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        oneTimeTransaction.setName("New OneTime Payment");

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .patch(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("name", is("OneTime Payment"));
    }

    @Test
    void givenExistingOneTimeTransactionAndBankAccountDoesNotExist_whenPatchTransaction_thenBadRequest() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var oneTimeTransaction = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("OneTime Payment")
                .description("For pizza last evening.")
                .date(LocalDate.now().toString())
                .type(TypeDto.EXPENSE)
                .amount(MonetaryAmountDto.builder().amount(14.99).build())
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("Thomas BankAccount").build())
                .labels(List.of("Friend"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        oneTimeTransaction.setName("New OneTime Payment");

        doReturn(Optional.empty()).when(bankAccountProvider).getBankAccount(anyString());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .patch(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("name", is("OneTime Payment"));
    }

    @Test
    void givenExistingOneTimeTransaction_whenDeleteTransaction_thenTransactionDoesNotExistAnymore() throws InterruptedException, JsonProcessingException {
        final var objectMapper = new ObjectMapper();

        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var oneTimeTransaction = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("OneTime Payment")
                .description("For pizza last evening.")
                .date(LocalDate.now().toString())
                .type(TypeDto.EXPENSE)
                .amount(MonetaryAmountDto.builder().amount(14.99).build())
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("Thomas BankAccount").build())
                .labels(List.of("Friend"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        final var consumerRecordAfterCreate = consumerRecords.poll(1, TimeUnit.SECONDS);
        assertNotNull(consumerRecordAfterCreate);
        final var recordAfterCreate = objectMapper.readTree(consumerRecordAfterCreate.value().toString());
        assertThat(recordAfterCreate.get("updateType").asText(), is("CREATE"));

        given().port(port)
                .contentType(ContentType.JSON)
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(0));

        final var consumedRecordAfterDelete = consumerRecords.poll(1, TimeUnit.SECONDS);
        assertNotNull(consumedRecordAfterDelete);

        final var recordAfterDelete = objectMapper.readTree(consumedRecordAfterDelete.value().toString());
        assertThat(recordAfterDelete.get("updateType").asText(), is("DELETE"));
        assertThat(recordAfterDelete.get("oneTimeTransaction").get("id").asText(), is(oneTimeTransaction.getId().toString()));
    }

    @Test
    void givenExistingOneTimeTransaction_whenDeleteTransactionByInvalidId_thenNotFound() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var oneTimeTransaction = OneTimeTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("OneTime Payment")
                .description("For pizza last evening.")
                .date(LocalDate.now().toString())
                .type(TypeDto.EXPENSE)
                .amount(MonetaryAmountDto.builder().amount(14.99).build())
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("Thomas BankAccount").build())
                .labels(List.of("Friend"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(oneTimeTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(1))
                .and().body("[0].id", is(oneTimeTransaction.getId().toString()));
    }
}
