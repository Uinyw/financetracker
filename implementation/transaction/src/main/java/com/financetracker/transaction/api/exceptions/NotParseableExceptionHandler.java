package com.financetracker.transaction.api.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class NotParseableExceptionHandler implements ExceptionMapper<NotParseableException> {
    @Override
    public Response toResponse(NotParseableException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}
