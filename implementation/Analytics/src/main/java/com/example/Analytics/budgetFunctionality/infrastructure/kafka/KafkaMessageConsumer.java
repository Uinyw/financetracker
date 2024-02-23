package com.example.Analytics.budgetFunctionality.infrastructure.kafka;

import com.example.Analytics.dietFunctionality.api.mapping.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.openapitools.client.model.ProductDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaMessageConsumer implements MessageConsumer {

    private final ProductService productService;

    @KafkaListener(topics = "product-update", groupId = "product")
    public void listenProductChange(ConsumerRecord<String, ProductDto> cr, @Payload ProductDto payload) {
        productService.receiveProduct(payload);
    }

}
