package com.example.Analytics.infrastructure.kafka;

import com.example.Analytics.infrastructure.kafka.config.UpdateType;
import com.example.Analytics.logic.model.budgetModel.VariableMonthlyTransaction;
import com.example.Analytics.logic.operations.BudgetService;
import com.example.Analytics.logic.operations.ProductService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.openapitools.client.model.ProductDto;
import org.openapitools.client.model.RecurringTransactionDto;
import org.openapitools.model.OneTimeTransactionDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class KafkaProductMessageConsumer{ //implements ProductMessageConsumer, VariableMonthlyTransactionMessageConsumer{//, FixedMonthlyTransactionMessageConsumer{

    private final ProductService productService;
    private final BudgetService budgetService;

    /*
    @KafkaListener(topics = "product-update", groupId = "product")
    public void listenProductChange(ConsumerRecord<String, JsonNode> cr, @Payload JsonNode payload){
        JsonNode product = payload.get("product");
        JsonNode amount = payload.get("amount");

        final var objectMapper = new ObjectMapper();

        ProductDto productDto = null;
        double productAmount = 0.0;

        try{
            productDto = objectMapper.treeToValue(product, ProductDto.class);
            productAmount = objectMapper.treeToValue(amount, double.class);
        }catch (Exception e){
            System.out.println("Json Parsing error\n"+e);
        }

        productService.receiveProduct(productDto, productAmount);
    }
*/
    /*
    @KafkaListener(topics = "oneTimeTransaction-update", groupId = "budget")
    public void listenFixedMonthlyTransactionChange(ConsumerRecord<String, JsonNode> cr, JsonNode payload) {
        JsonNode jsonOneTimeTransactionDto = payload.get("oneTimeTransaction");
        JsonNode jsonUpdateType = payload.get("updateType");

        final var objectMapper = new ObjectMapper();

        OneTimeTransactionDto oneTimeTransactionDto = null;
        UpdateType updateType = null;

        try{
            oneTimeTransactionDto = objectMapper.treeToValue(jsonOneTimeTransactionDto, OneTimeTransactionDto.class);
            updateType = objectMapper.treeToValue(jsonUpdateType, UpdateType.class);
        }catch (Exception e){
            System.out.println("Json Parsing error\n"+e);
        }

        budgetService.variableMonthlyTransactionChange(oneTimeTransactionDto, updateType);
    }
    @KafkaListener(topics = "recurringTransaction-update", groupId = "budget")
    public void listenVariableMonthlyTransactionChange(ConsumerRecord<String, JsonNode> cr, JsonNode payload) {
        JsonNode jsonOneTimeTransactionDto = payload.get("recurringTransaction");
        JsonNode jsonUpdateType = payload.get("updateType");

        final var objectMapper = new ObjectMapper();

        RecurringTransactionDto recurringTransactionDto = null;
        UpdateType updateType = null;

        try{
            recurringTransactionDto = objectMapper.treeToValue(jsonOneTimeTransactionDto, RecurringTransactionDto.class);
            updateType = objectMapper.treeToValue(jsonUpdateType, UpdateType.class);
        }catch (Exception e){
            System.out.println("Json Parsing error\n"+e);
        }

        budgetService.fixedMonthlyTransactionChange(recurringTransactionDto, updateType);
    }
*/
}
