package com.party.entry.reservationentry.kafka;

import com.party.entry.reservationentry.dto.Reservation;

public interface ReservationMessageService {
    void bookReservation(Reservation reservation);
}
