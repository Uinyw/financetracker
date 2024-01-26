package com.financetracker.product.api.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class CustomException extends WebApplicationException {
    public CustomException() {
        super(Response.Status.BAD_REQUEST.getStatusCode());
    }

    public CustomException(final String message) {
        super(message, Response.Status.BAD_REQUEST.getStatusCode());
    }
}
