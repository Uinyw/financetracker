package com.example.Analytics;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@EmbeddedKafka(topics = {"product-update", "oneTimeTransaction-update", "recurringTransaction-update"})
public class IntegrationTestBase {

    @LocalServerPort
    protected int port;

    protected static final String LOCAL_BASE_URL_WITHOUT_PORT = "http://localhost";

}
