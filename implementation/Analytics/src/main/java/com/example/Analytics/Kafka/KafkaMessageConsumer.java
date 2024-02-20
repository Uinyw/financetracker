package com.example.Analytics.Kafka;

import com.example.Analytics.DietFunctionality.DietMapper.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.openapitools.client.model.BankAccountDto;
import org.openapitools.client.model.ProductDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaMessageConsumer implements MessageConsumer {

    private final ProductService productService;

    @KafkaListener(topics = "product-update", groupId = "product")
    public void listenBankAccountChange(ConsumerRecord<String, ProductDto> cr, @Payload ProductDto payload) {
        productService.receiveProduct(payload);
    }

}
