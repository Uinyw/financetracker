package com.bank.account.infrastructure.Kafka;

import com.bank.account.infrastructure.BankAccountRepository;
import com.bank.account.logic.operations.BankAccountService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.openapitools.client.model.OneTimeTransactionDto;
import org.openapitools.client.model.TransferDto;
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
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@RestController
public class KafkaRessource {

    private static final Logger logger =
            LoggerFactory.getLogger(KafkaRessource.class);

    private final KafkaTemplate<String, Object> template;
    private final BankAccountService bankAccountService;
    private final String topic1Name;
    private final int messagesPerRequest;

    //If new topic is defined, it also as to be set here!!
    public KafkaRessource(
            final KafkaTemplate<String, Object> template, BankAccountService bankAccountService,
            @Value("${tpd.topic1-name}") final String topic1Name,
            @Value("${tpd.messages-per-request}") final int messagesPerRequest) {
        this.template = template;
        this.bankAccountService = bankAccountService;
        this.topic1Name = topic1Name;
        this.messagesPerRequest = messagesPerRequest;
    }


    @KafkaListener(topics = "transaction-backwards-queue", clientIdPrefix = "json",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenForTransactions(ConsumerRecord<String, OneTimeTransactionDto> cr,
                               @Payload OneTimeTransactionDto payload) {
        logger.info("Logger 1 [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                typeIdHeader(cr.headers()), payload, cr.toString());
        try{
            bankAccountService.transfer(payload);
        }catch (Exception e){
            System.out.println("A problem with the Object occurred\n" + e);
        }
    }


    private static String typeIdHeader(Headers headers) {
        return StreamSupport.stream(headers.spliterator(), false)
                .filter(header -> header.key().equals("__TypeId__"))
                .findFirst().map(header -> new String(header.value())).orElse("N/A");
    }
}
