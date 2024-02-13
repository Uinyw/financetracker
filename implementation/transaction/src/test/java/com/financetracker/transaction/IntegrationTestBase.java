package com.financetracker.transaction;

import com.financetracker.transaction.infrastructure.client.BankAccountProvider;
import com.financetracker.transaction.infrastructure.db.OneTimeTransactionRepository;
import com.financetracker.transaction.infrastructure.db.RecurringTransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTestBase {

    @LocalServerPort
    protected int port;

    protected static final String LOCAL_BASE_URL_WITHOUT_PORT = "http://localhost";

    @Autowired
    private OneTimeTransactionRepository oneTimeTransactionRepository;

    @Autowired
    private RecurringTransactionRepository recurringTransactionRepository;

    @SpyBean
    public BankAccountProvider bankAccountProvider;

    @AfterEach
    void tearDown() {
        Mockito.reset(bankAccountProvider);
        oneTimeTransactionRepository.deleteAll();
        recurringTransactionRepository.deleteAll();
    }
}
