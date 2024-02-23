package com.example.Analytics.dietFunctionality.infrastructure.kafka;

import com.example.Analytics.dietFunctionality.logic.operations.ProductService;
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
public class KafkaProductMessageConsumer implements ProductMessageConsumer {

    private final ProductService productService;
    private static ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "product-update", groupId = "product")
    public void listenProductChange(ConsumerRecord<String, JsonNode> cr, @Payload JsonNode payload){
        JsonNode product = payload.get("product");
        JsonNode amount = payload.get("amount");

        ProductDto productDto = null;
        double productAmount = 0.0;

        try{
            productDto = mapper.treeToValue(product, ProductDto.class);
            productAmount = mapper.treeToValue(amount, double.class);
        }catch (Exception e){
            System.out.println("Json Parsing error\n"+e);
        }

        productService.receiveProduct(productDto, productAmount);
    }

}
