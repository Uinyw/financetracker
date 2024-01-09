package com.financetracker.transaction.api.config;

import com.financetracker.transaction.api.TransactionResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionConfig extends ResourceConfig {

    public TransactionConfig() {
        register(TransactionResource.class);
    }
}
