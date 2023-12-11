package com.financetracker.transaction;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.springframework.stereotype.Component;

@Component
@Path("/hello")
public class TransactionResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello from Spring";
    }
}
