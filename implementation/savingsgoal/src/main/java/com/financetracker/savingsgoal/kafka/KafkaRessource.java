package com.financetracker.savingsgoal.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.openapitools.client.model.*;
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
    private final String topic2Name;
    private final String topic3Name;
    private final int messagesPerRequest;
    private CountDownLatch latch;

    //If new topic is defined, it also as to be set here!!
    public KafkaRessource(
            final KafkaTemplate<String, Object> template,
            @Value("${tpd.topic1-name}") final String topic1Name,
            @Value("${tpd.topic2-name}") final String topic2Name,
            @Value("${tpd.topic3-name}") final String topic3Name,
            @Value("${tpd.messages-per-request}") final int messagesPerRequest) {
        this.template = template;
        this.topic1Name = topic1Name;
        this.topic2Name = topic2Name;
        this.topic3Name = topic3Name;
        this.messagesPerRequest = messagesPerRequest;
    }

    //TODO to be deleted
    @GetMapping("/transactionTest")
    public String testReceive() throws Exception {
        latch = new CountDownLatch(messagesPerRequest); //Messages are duplicated
        IntStream.range(0, messagesPerRequest)
                .forEach(i -> this.template.send(topic1Name, String.valueOf(i), createRandomOneTimeTransactionDto())
                );
        latch.await(60, TimeUnit.SECONDS);
        logger.info("All messages received");
        return "new Transaction!";
    }

    //TODO to be deleted
    @GetMapping("/transaction2")
    public Boolean sendTestMessage(OneTimeTransactionDto transaction) throws Exception{
        this.template.send(topic1Name, createRandomOneTimeTransactionDto());//TODO replace the random one time transaction
        logger.info("Message put in queue");
    return true;
    }
    @GetMapping("/transaction")
    public Boolean sendMessage(OneTimeTransactionDto transaction) throws Exception{
        this.template.send(topic2Name, transaction);
        logger.info("Message put in queue");
        return true;
    }

    //TODO to be deleted
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
        oneTimeTransactionDto.setTransferStatus(TransferStatusDto.INITIAL);

        return oneTimeTransactionDto;
    }
/*
    @KafkaListener(topics = "transaction-queue", clientIdPrefix = "json",
            containerFactory = "OneTimeTransactionDtoKafkaListenerContainerFactory", groupId = "topic1")
    public void listenAsObject(ConsumerRecord<String, OneTimeTransactionDto> cr,
                               @Payload OneTimeTransactionDto payload) {
        logger.info("Logger 1 [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                typeIdHeader(cr.headers()), payload, cr.toString());
        latch.countDown();
    }
*/
    @KafkaListener(topics = "account-update", clientIdPrefix = "json",
            containerFactory = "bankAccountDtoKafkaListenerContainerFactory", groupId = "topic2")
    public void listenBankAccountChange(ConsumerRecord<String, BankAccountDto> cr,
                               @Payload BankAccountDto payload) {
        logger.info("Logger 1 [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                typeIdHeader(cr.headers()), payload, cr.toString());
        latch.countDown();
        System.out.println("listener");
    }

    private static String typeIdHeader(Headers headers) {
        return StreamSupport.stream(headers.spliterator(), false)
                .filter(header -> header.key().equals("__TypeId__"))
                .findFirst().map(header -> new String(header.value())).orElse("N/A");
    }
}
