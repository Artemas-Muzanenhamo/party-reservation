package com.party.entry.reservationentry.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.party.entry.reservationentry.dto.Reservation;

public interface ReservationService {
    void bookReservation(Reservation reservation);
    void reserveBooking(Reservation reservation) throws JsonProcessingException;
}
