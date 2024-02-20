package com.bank.account;

import com.bank.account.infrastructure.BankAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@EmbeddedKafka(topics = "bankaccount-update")
public class IntegrationTestBase {

    @LocalServerPort
    protected int port;

    protected static final String LOCAL_BASE_URL_WITHOUT_PORT = "http://localhost";

    @Autowired
    protected BankAccountRepository bankAccountRepository;

    @AfterEach
    void tearDown() {
        bankAccountRepository.deleteAll();
    }
}
