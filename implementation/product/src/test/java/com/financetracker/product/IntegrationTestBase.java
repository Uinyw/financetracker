package com.financetracker.product;

import com.financetracker.product.infrastructure.db.ProductEntryRepository;
import com.financetracker.product.infrastructure.db.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTestBase {

    @LocalServerPort
    protected int port;

    protected static final String LOCAL_BASE_URL_WITHOUT_PORT = "http://localhost";

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected ProductEntryRepository productEntryRepository;

    @AfterEach
    void tearDown() {
        productEntryRepository.deleteAll();
        productRepository.deleteAll();
    }
}
