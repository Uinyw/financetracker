package com.financetracker.transaction.api.exceptions;

public class TransferFailedException extends CustomException {

    public TransferFailedException(final String message) {
        super(message);
    }

}
