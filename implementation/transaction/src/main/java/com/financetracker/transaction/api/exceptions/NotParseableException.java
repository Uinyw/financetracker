package com.financetracker.transaction.api.exceptions;

import jakarta.ws.rs.core.Response;

import javax.ws.rs.WebApplicationException;

public class NotParseableException extends WebApplicationException {

    public NotParseableException() {
        super(Response.Status.BAD_REQUEST.getStatusCode());
    }
}
