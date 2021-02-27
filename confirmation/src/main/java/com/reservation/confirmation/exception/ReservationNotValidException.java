package com.reservation.confirmation.exception;

public class ReservationNotValidException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ReservationNotValidException(String message) {
        super(message);
    }
}
