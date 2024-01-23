package com.bank.account.infrastructure.Kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.openapitools.client.model.OneTimeTransactionDto;
import org.openapitools.model.MonetaryAmountDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@RestController
public class KafkaRessource {

    private static final Logger logger =
            LoggerFactory.getLogger(KafkaRessource.class);

    private final KafkaTemplate<String, Object> template;
    private final String topic1Name;
    private final int messagesPerRequest;
    private CountDownLatch latch;

    //If new topic is defined, it also as to be set here!!
    public KafkaRessource(
            final KafkaTemplate<String, Object> template,
            @Value("${tpd.topic1-name}") final String topic1Name,
            @Value("${tpd.messages-per-request}") final int messagesPerRequest) {
        this.template = template;
        this.topic1Name = topic1Name;
        this.messagesPerRequest = messagesPerRequest;
    }

    @KafkaListener(topics = "transaction-queue", clientIdPrefix = "json",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenAsObject(ConsumerRecord<String, OneTimeTransactionDto> cr,
                               @Payload OneTimeTransactionDto payload) {
        logger.info("Logger 1 [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                typeIdHeader(cr.headers()), payload, cr.toString());
        latch.countDown();
        //TODO get the transferDTO and add the values for the bank accounts
        //Transfer transfer = oneTimeTransactionDto.getTransfer();
        //UUID source = transfer.getSourceBankAccountId();
        //UUID target = transfer.getTargetBankAccountId();
        //oneTimeTransactionDto.getAmount();
        //TODO get the bank accounts and add the values
    }


    private static String typeIdHeader(Headers headers) {
        return StreamSupport.stream(headers.spliterator(), false)
                .filter(header -> header.key().equals("__TypeId__"))
                .findFirst().map(header -> new String(header.value())).orElse("N/A");
    }
}
