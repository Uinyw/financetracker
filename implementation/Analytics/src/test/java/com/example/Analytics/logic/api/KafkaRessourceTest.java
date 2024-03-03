package com.example.Analytics.logic.api;

import com.example.Analytics.IntegrationTestBase;
import com.example.Analytics.api.mapping.TransactionMapper;
import com.example.Analytics.infrastructure.kafka.KafkaProductMessageConsumer;
import com.example.Analytics.infrastructure.kafka.KafkaTransactionMessageConsumer;
import com.example.Analytics.infrastructure.kafka.config.UpdateType;
import com.example.Analytics.logic.operations.BudgetService;
import com.example.Analytics.logic.operations.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.MonetaryAmountDto;
import org.openapitools.client.model.OneTimeTransactionDto;
import org.openapitools.client.model.ProductDto;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openapitools.client.model.RecurringTransactionDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
public class KafkaRessourceTest extends IntegrationTestBase {

    @Mock
    private ProductService productService;
    @InjectMocks
    private KafkaProductMessageConsumer kafkaProductMessageConsumer;
    @Mock
    private TransactionMapper transactionMapper;
    @Mock
    private BudgetService budgetService;

    @InjectMocks
    private KafkaTransactionMessageConsumer kafkaTransactionMessageConsumer;

    @Test
    public void testListenProductChange() {
        String key = "testKey";
        final var objectMapper = new ObjectMapper();
        final var node = objectMapper.createObjectNode();
        node.set("product", objectMapper.valueToTree(ProductDto.builder().build()));
        node.put("amount", 2.0);


        ConsumerRecord<String, JsonNode> consumerRecord = new ConsumerRecord<>("product-update", 0, 0, key, node);
        kafkaProductMessageConsumer.listenProductChange(consumerRecord, node);

        verify(productService).receiveProduct(any(ProductDto.class), anyDouble());
    }

    @Test
    public void testListenFixedMonthlyTransactionChange() {
        final var objectMapper = new ObjectMapper();
        final var node = objectMapper.createObjectNode();

        node.set("oneTimeTransaction", objectMapper.valueToTree(OneTimeTransactionDto.builder()
                        .id(UUID.randomUUID()).labels(List.of("a","b")).amount(new MonetaryAmountDto(2.0)).date(LocalDate.now().toString())
                .build()));
        node.put("updateType", UpdateType.CREATE.toString());

        String key = "someKey";
        ConsumerRecord<String, JsonNode> consumerRecord = new ConsumerRecord<>("oneTimeTransaction-update", 0, 0, key, node);

        kafkaTransactionMessageConsumer.listenFixedMonthlyTransactionChange(consumerRecord, node);
        verify(budgetService).variableMonthlyTransactionChange(anyList(), any(UpdateType.class));
    }

    @Test
    public void testListenVariableMonthlyTransactionChange() {
        final var objectMapper = new ObjectMapper();
        final var node = objectMapper.createObjectNode();

        node.set("oneTimeTransaction", objectMapper.valueToTree(RecurringTransactionDto.builder()
                .id(UUID.randomUUID()).labels(List.of("a","b")).fixedAmount(new MonetaryAmountDto(2.0))                .build()));
        node.put("updateType", UpdateType.CREATE.toString());

        String key = "someKey";
        ConsumerRecord<String, JsonNode> consumerRecord = new ConsumerRecord<>("recurringTransaction-update", 0, 0, key, node);

        kafkaTransactionMessageConsumer.listenVariableMonthlyTransactionChange(consumerRecord, node);
        verify(budgetService).fixedTransactionChange(anyList(), any(UpdateType.class));
    }

}
