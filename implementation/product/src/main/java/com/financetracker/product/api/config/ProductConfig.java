package com.financetracker.product.api.config;

import com.financetracker.product.api.ProductResource;
import com.financetracker.product.api.ShoppingCartResource;
import com.financetracker.product.api.exceptions.ExceptionHandler;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfig extends ResourceConfig {

    public ProductConfig() {
        register(ProductResource.class);
        register(ShoppingCartResource.class);
        register(CorsFilter.class);
        register(ExceptionHandler.class);
    }
}
