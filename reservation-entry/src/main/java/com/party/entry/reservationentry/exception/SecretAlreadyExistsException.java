package com.party.entry.reservationentry.exception;

public class SecretAlreadyExistsException extends RuntimeException {
    public SecretAlreadyExistsException() {
    }

    public SecretAlreadyExistsException(String message) {
        super(message);
    }
}
