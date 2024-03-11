package com.financetracker.product.infrastructure.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.financetracker.product.api.mapping.ProductMapper;
import com.financetracker.product.logic.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Component
public class KafkaMessagePublisher implements MessagePublisher {

    private final KafkaTemplate<String, Object> template;
    private final ProductMapper productMapper;

    @Value("${tpd.topic-product-update}")
    private String productUpdate;

    public void publishMessageSuppliesUpdate(final Product product, final BigDecimal delta) {
        final var objectMapper = new ObjectMapper();
        final var node = objectMapper.createObjectNode();
        final var productDto = productMapper.mapProductModelToDto(product);

        node.set("product", objectMapper.valueToTree(productDto));
        node.put("amount", delta.doubleValue());

        this.template.send(productUpdate, node);
    }

}
