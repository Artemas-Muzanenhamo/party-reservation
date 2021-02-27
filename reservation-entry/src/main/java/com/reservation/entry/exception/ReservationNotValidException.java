package com.reservation.entry.exception;

public class ReservationNotValidException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ReservationNotValidException() {
    }

    public ReservationNotValidException(String message) {
        super(message);
    }
}
