package com.financetracker.transaction.infrastructure.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.financetracker.transaction.api.mapping.OneTimeTransactionMapper;
import com.financetracker.transaction.api.mapping.RecurringTransactionMapper;
import com.financetracker.transaction.infrastructure.kafka.config.UpdateType;
import com.financetracker.transaction.logic.model.OneTimeTransaction;
import com.financetracker.transaction.logic.model.RecurringTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class KafkaMessagePublisher implements MessagePublisher {

    private final KafkaTemplate<String, Object> template;
    private final OneTimeTransactionMapper oneTimeTransactionMapper;
    private final RecurringTransactionMapper recurringTransactionMapper;

    @Value("${tpd.topic-oneTimeTransaction-update}")
    private String oneTimeTransactionUpdate;

    @Value("${tpd.topic-recurringTransaction-update}")
    private String recurringTransactionUpdate;

    public void publishMessageOneTimeTransactionUpdate(final OneTimeTransaction oneTimeTransaction, final UpdateType updateType) {
            final var objectMapper = new ObjectMapper();
            final var node = objectMapper.createObjectNode();
            final var productDto = oneTimeTransactionMapper.mapOneTimeTransactionModelToDto(oneTimeTransaction);

            node.set("oneTimeTransaction", objectMapper.valueToTree(productDto));
            node.put("updateType", updateType.toString());

            this.template.send(oneTimeTransactionUpdate, node);
    }

    public void publishMessageRecurringTransactionUpdate(final RecurringTransaction recurringTransaction, final UpdateType updateType) {
        final var objectMapper = new ObjectMapper();
        final var node = objectMapper.createObjectNode();
        final var productDto = recurringTransactionMapper.mapRecurringTransactionModelToDto(recurringTransaction);

        node.set("recurringTransaction", objectMapper.valueToTree(productDto));
        node.put("updateType", updateType.toString());

        this.template.send(recurringTransactionUpdate, node);
    }

}
