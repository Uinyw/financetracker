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
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaProductMessageConsumer {

    private final ProductService productService;

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

}
