package com.reservation.entry.exception;

public class SecretAlreadyExistsException extends RuntimeException {
    public SecretAlreadyExistsException() {
    }

    public SecretAlreadyExistsException(String message) {
        super(message);
    }
}
