package com.financetracker.transaction.api;

import com.fasterxml.jackson.core.JsonProcessingException;
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

class TransactionResourceRecurringTest extends IntegrationTestBase {

    @Value("${tpd.topic-recurringTransaction-update}")
    private String recurringTransactionUpdate;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    private KafkaMessageListenerContainer<String, Object> container;
    private BlockingQueue<ConsumerRecord<String, Object>> consumerRecords;

    @BeforeEach
    void setup() {
        consumerRecords = new LinkedBlockingQueue<>();

        Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps("test-group-id", "false", embeddedKafkaBroker);
        DefaultKafkaConsumerFactory<String, Object> consumer = new DefaultKafkaConsumerFactory<>(consumerProperties);

        ContainerProperties containerProperties = new ContainerProperties(recurringTransactionUpdate);
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
    void givenRecurringTransactionAndBankAccountExists_whenCreateRecurringTransaction_thenTransactionExistsInSystem() throws InterruptedException, JsonProcessingException {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var recurringTransaction = RecurringTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Employment Income")
                .description("Employer: KIT")
                .type(TypeDto.INCOME)
                .startDate(LocalDate.now().toString())
                .fixedAmount(MonetaryAmountDto.builder().amount(1300.0).build())
                .periodicity(PeriodicityDto.MONTHLY)
                .transfer(TransferDto.builder().externalSourceId("KIT").targetBankAccountId(UUID.randomUUID()).build())
                .labels(List.of("KIT"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(1))
                .and().body("[0].id", is(recurringTransaction.getId().toString()))
                .and().body("[0].name", is(recurringTransaction.getName()))
                .and().body("[0].description", is(recurringTransaction.getDescription()))
                .and().body("[0].startDate", is(recurringTransaction.getStartDate()))
                .and().body("[0].type", is(recurringTransaction.getType().toString()))
                .and().body("[0].periodicity", is(recurringTransaction.getPeriodicity().toString()))
                .and().body("[0].fixedAmount.amount", is(1300.0F))
                .and().body("[0].transfer.sourceBankAccountId", is(nullValue()))
                .and().body("[0].transfer.externalSourceId", is(recurringTransaction.getTransfer().getExternalSourceId()))
                .and().body("[0].transfer.targetBankAccountId", is(recurringTransaction.getTransfer().getTargetBankAccountId().toString()))
                .and().body("[0].transfer.externalTargetId", is(nullValue()))
                .and().body("[0].labels.size()", is(1))
                .and().body("[0].labels[0]", is("KIT"));

        final var consumedRecord = consumerRecords.poll(1, TimeUnit.SECONDS);
        assertNotNull(consumedRecord);

        final var objectMapper = new ObjectMapper();
        final var record = objectMapper.readTree(consumedRecord.value().toString());
        assertThat(record.get("updateType").asText(), is("CREATE"));
        assertThat(record.get("recurringTransaction").get("id").asText(), is(recurringTransaction.getId().toString()));
    }

    @Test
    void givenRecurringTransactionAndBankAccountDoesNotExist_whenCreateRecurringTransaction_thenBadRequest() {
        doReturn(Optional.empty()).when(bankAccountProvider).getBankAccount(anyString());

        final var recurringTransaction = RecurringTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Employment Income")
                .description("Employer: KIT")
                .type(TypeDto.INCOME)
                .startDate(LocalDate.now().toString())
                .fixedAmount(MonetaryAmountDto.builder().amount(1300.0).build())
                .periodicity(PeriodicityDto.MONTHLY)
                .transfer(TransferDto.builder().externalSourceId("KIT").targetBankAccountId(UUID.randomUUID()).build())
                .labels(List.of("KIT"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(0));
    }

    @Test
    void givenExistingRecurringTransaction_whenGetTransactionById_thenTransactionIsReturned() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var recurringTransaction = RecurringTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Rent")
                .description("Landlord: HaDiKo")
                .type(TypeDto.EXPENSE)
                .startDate(LocalDate.now().toString())
                .fixedAmount(MonetaryAmountDto.builder().amount(300.0).build())
                .periodicity(PeriodicityDto.MONTHLY)
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("HaDiKo").build())
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("id", is(recurringTransaction.getId().toString()));
    }

    @Test
    void givenExistingRecurringTransaction_whenGetTransactionByTargetBankAccount_thenTransactionIsReturned() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var recurringTransaction = RecurringTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Employment Income")
                .description("Employer: KIT")
                .type(TypeDto.INCOME)
                .startDate(LocalDate.now().toString())
                .fixedAmount(MonetaryAmountDto.builder().amount(1300.0).build())
                .periodicity(PeriodicityDto.MONTHLY)
                .transfer(TransferDto.builder().externalSourceId("KIT").targetBankAccountId(UUID.randomUUID()).build())
                .labels(List.of("KIT"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring?targetBankAccount=" + recurringTransaction.getTransfer().getTargetBankAccountId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(1))
                .and().body("[0].id", is(recurringTransaction.getId().toString()));
    }

    @Test
    void givenExistingRecurringTransaction_whenGetTransactionBySourceBankAccount_thenTransactionIsReturned() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var recurringTransaction = RecurringTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Rent")
                .description("Landlord: HaDiKo")
                .type(TypeDto.EXPENSE)
                .startDate(LocalDate.now().toString())
                .fixedAmount(MonetaryAmountDto.builder().amount(300.0).build())
                .periodicity(PeriodicityDto.MONTHLY)
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("HaDiKo").build())
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring?sourceBankAccount=" + recurringTransaction.getTransfer().getSourceBankAccountId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(1))
                .and().body("[0].id", is(recurringTransaction.getId().toString()));
    }

    @Test
    void givenExistingRecurringTransaction_whenGetTransactionByInvalidId_thenNotFound() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var recurringTransaction = RecurringTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Rent")
                .description("Landlord: HaDiKo")
                .type(TypeDto.EXPENSE)
                .startDate(LocalDate.now().toString())
                .fixedAmount(MonetaryAmountDto.builder().amount(300.0).build())
                .periodicity(PeriodicityDto.MONTHLY)
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).externalTargetId("HaDiKo").build())
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void givenExistingRecurringTransaction_whenPatchTransaction_thenTransactionIsUpdated() throws InterruptedException, JsonProcessingException {
        final var objectMapper = new ObjectMapper();

        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var recurringTransaction = RecurringTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Employment Income")
                .description("Employer: KIT")
                .type(TypeDto.INCOME)
                .startDate(LocalDate.now().toString())
                .fixedAmount(MonetaryAmountDto.builder().amount(1300.0).build())
                .periodicity(PeriodicityDto.MONTHLY)
                .transfer(TransferDto.builder().externalSourceId("KIT").targetBankAccountId(UUID.randomUUID()).build())
                .labels(List.of("KIT"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        final var consumerRecordAfterCreate = consumerRecords.poll(1, TimeUnit.SECONDS);
        assertNotNull(consumerRecordAfterCreate);
        final var recordAfterCreate = objectMapper.readTree(consumerRecordAfterCreate.value().toString());
        assertThat(recordAfterCreate.get("updateType").asText(), is("CREATE"));

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("name", is("Employment Income"));

        recurringTransaction.setName("New Employment Income");

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .patch(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("name", is("New Employment Income"));

        final var consumedRecordAfterPatch = consumerRecords.poll(1, TimeUnit.SECONDS);
        assertNotNull(consumedRecordAfterPatch);

        final var recordAfterPatch = objectMapper.readTree(consumedRecordAfterPatch.value().toString());
        assertThat(recordAfterPatch.get("updateType").asText(), is("UPDATE"));
        assertThat(recordAfterPatch.get("recurringTransaction").get("id").asText(), is(recurringTransaction.getId().toString()));
        assertThat(recordAfterPatch.get("recurringTransaction").get("name").asText(), is("New Employment Income"));
    }

    @Test
    void givenExistingRecurringTransaction_whenPatchTransactionByInvalidId_thenNotFound() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var recurringTransaction = RecurringTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Employment Income")
                .description("Employer: KIT")
                .type(TypeDto.INCOME)
                .startDate(LocalDate.now().toString())
                .fixedAmount(MonetaryAmountDto.builder().amount(1300.0).build())
                .periodicity(PeriodicityDto.MONTHLY)
                .transfer(TransferDto.builder().externalSourceId("KIT").targetBankAccountId(UUID.randomUUID()).build())
                .labels(List.of("KIT"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        recurringTransaction.setName("New Employment Income");

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .patch(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("name", is("Employment Income"));
    }

    @Test
    void givenExistingRecurringTransactionAndBankAccountDoesNotExist_whenPatchTransaction_thenBadRequest() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var recurringTransaction = RecurringTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Employment Income")
                .description("Employer: KIT")
                .type(TypeDto.INCOME)
                .startDate(LocalDate.now().toString())
                .fixedAmount(MonetaryAmountDto.builder().amount(1300.0).build())
                .periodicity(PeriodicityDto.MONTHLY)
                .transfer(TransferDto.builder().externalSourceId("KIT").targetBankAccountId(UUID.randomUUID()).build())
                .labels(List.of("KIT"))
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        recurringTransaction.setName("New Employment Income");

        doReturn(Optional.empty()).when(bankAccountProvider).getBankAccount(anyString());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .patch(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("name", is("Employment Income"));
    }

    @Test
    void givenExistingRecurringTransaction_whenDeleteTransaction_thenTransactionDoesNotExistAnymore() throws InterruptedException, JsonProcessingException {
        final var objectMapper = new ObjectMapper();

        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var recurringTransaction = RecurringTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Saving")
                .description("I want to save money")
                .type(TypeDto.SHIFT)
                .startDate(LocalDate.now().toString())
                .periodicity(PeriodicityDto.HALF_YEARLY)
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).targetBankAccountId(UUID.randomUUID()).build())
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        final var consumerRecordAfterCreate = consumerRecords.poll(1, TimeUnit.SECONDS);
        assertNotNull(consumerRecordAfterCreate);
        final var recordAfterCreate = objectMapper.readTree(consumerRecordAfterCreate.value().toString());
        assertThat(recordAfterCreate.get("updateType").asText(), is("CREATE"));

        given().port(port)
                .contentType(ContentType.JSON)
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + recurringTransaction.getId().toString())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(0));

        final var consumedRecordAfterDelete = consumerRecords.poll(1, TimeUnit.SECONDS);
        assertNotNull(consumedRecordAfterDelete);

        final var recordAfterPatch = objectMapper.readTree(consumedRecordAfterDelete.value().toString());
        assertThat(recordAfterPatch.get("updateType").asText(), is("DELETE"));
        assertThat(recordAfterPatch.get("recurringTransaction").get("id").asText(), is(recurringTransaction.getId().toString()));
    }

    @Test
    void givenExistingOneTimeTransaction_whenDeleteTransactionByInvalidId_thenNotFound() {
        doReturn(Optional.of(BankAccountDto.builder().build())).when(bankAccountProvider).getBankAccount(anyString());

        final var recurringTransaction = RecurringTransactionDto.builder()
                .id(UUID.randomUUID())
                .name("Saving")
                .description("I want to save money")
                .type(TypeDto.SHIFT)
                .startDate(LocalDate.now().toString())
                .periodicity(PeriodicityDto.HALF_YEARLY)
                .transfer(TransferDto.builder().sourceBankAccountId(UUID.randomUUID()).targetBankAccountId(UUID.randomUUID()).build())
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(recurringTransaction)
                .post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given().port(port)
                .contentType(ContentType.JSON)
                .delete(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring/" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        given().port(port)
                .get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/recurring")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().body("size()", is(1))
                .and().body("[0].id", is(recurringTransaction.getId().toString()));
    }

}
