package com.financetracker.product.api.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class ExceptionHandler implements ExceptionMapper<CustomException> {
    @Override
    public Response toResponse(CustomException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}
