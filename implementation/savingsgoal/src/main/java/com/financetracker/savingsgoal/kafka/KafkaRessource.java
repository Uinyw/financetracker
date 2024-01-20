package com.financetracker.savingsgoal.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.openapitools.client.model.MonetaryAmountDto;
import org.openapitools.client.model.OneTimeTransactionDto;
import org.openapitools.client.model.TransferStatusDto;
import org.openapitools.client.model.TypeDto;
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
    private final String topicName;
    private final int messagesPerRequest;
    private CountDownLatch latch;

    public KafkaRessource(
            final KafkaTemplate<String, Object> template,
            @Value("${tpd.topic-name}") final String topicName,
            @Value("${tpd.messages-per-request}") final int messagesPerRequest) {
        this.template = template;
        this.topicName = topicName;
        this.messagesPerRequest = messagesPerRequest;
    }

    //TODO to be deleted
    @GetMapping("/transaction")
    public String hello() throws Exception {
        latch = new CountDownLatch(messagesPerRequest); //Messages are duplicated
        IntStream.range(0, messagesPerRequest)
                .forEach(i -> this.template.send(topicName, String.valueOf(i), createRandomOneTimeTransactionDto())
                );
        latch.await(60, TimeUnit.SECONDS);
        logger.info("All messages received");
        return "new Transaction!";
    }

    private OneTimeTransactionDto createRandomOneTimeTransactionDto(){
        OneTimeTransactionDto oneTimeTransactionDto = new OneTimeTransactionDto();
        MonetaryAmountDto monetaryAmountDto = new MonetaryAmountDto();
        monetaryAmountDto.setAmount((double)Math.round(Math.random()*1000));
        oneTimeTransactionDto.setAmount(monetaryAmountDto);
        oneTimeTransactionDto.setType(TypeDto.INCOME);
        oneTimeTransactionDto.setName("random Name");
        oneTimeTransactionDto.setDate(LocalDate.now().toString());
        oneTimeTransactionDto.setId(UUID.randomUUID());
        oneTimeTransactionDto.setDescription("random Description");
        oneTimeTransactionDto.setTransferStatus(TransferStatusDto.SUCCESSFUL);

        return oneTimeTransactionDto;
    }

    @KafkaListener(topics = "transaction-queue", clientIdPrefix = "json",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenAsObject(ConsumerRecord<String, OneTimeTransactionDto> cr,
                               @Payload OneTimeTransactionDto payload) {
        logger.info("Logger 1 [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                typeIdHeader(cr.headers()), payload, cr.toString());
        latch.countDown();
    }


    private static String typeIdHeader(Headers headers) {
        return StreamSupport.stream(headers.spliterator(), false)
                .filter(header -> header.key().equals("__TypeId__"))
                .findFirst().map(header -> new String(header.value())).orElse("N/A");
    }
}
