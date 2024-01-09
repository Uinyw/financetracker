package com.financetracker.transaction;

import com.financetracker.transaction.api.mapping.OneTimeTransactionMapper;
import com.financetracker.transaction.api.mapping.RecurringTransactionMapper;
import com.financetracker.transaction.infrastructure.client.BankAccountProvider;
import com.financetracker.transaction.infrastructure.db.OneTimeTransactionRepository;
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
    public OneTimeTransactionMapper oneTimeTransactionMapper;
    @Autowired
    public RecurringTransactionMapper recurringTransactionMapper;

    @SpyBean
    public BankAccountProvider bankAccountProvider;

    @AfterEach
    void tearDown() {
        Mockito.reset(bankAccountProvider);
        oneTimeTransactionRepository.deleteAll();
    }
}
