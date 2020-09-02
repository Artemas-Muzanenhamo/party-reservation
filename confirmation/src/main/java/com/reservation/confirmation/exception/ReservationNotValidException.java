package com.reservation.confirmation.exception;

public class ReservationNotValidException extends RuntimeException {
    public ReservationNotValidException(String message) {
        super(message);
    }
}
