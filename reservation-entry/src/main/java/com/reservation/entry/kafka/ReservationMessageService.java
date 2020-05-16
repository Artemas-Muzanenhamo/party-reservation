package com.reservation.entry.kafka;

import com.reservation.entry.dto.Reservation;

public interface ReservationMessageService {
    void bookReservation(Reservation reservation);
}
