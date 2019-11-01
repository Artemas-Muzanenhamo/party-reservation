package com.party.entry.reservationentry.exception;

public class ReservationNotValidException extends RuntimeException {
    public ReservationNotValidException() { }

    public ReservationNotValidException(String message) {
        super(message);
    }
}
