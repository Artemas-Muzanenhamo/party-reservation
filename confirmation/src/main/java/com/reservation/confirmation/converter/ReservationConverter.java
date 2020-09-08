package com.reservation.confirmation.converter;

import com.reservation.confirmation.domain.Reservation;
import com.reservation.message.ReservationMessageJson;

public class ReservationConverter {
    public static Reservation toReservation(ReservationMessageJson json) {
        return new Reservation(json.secret, json.name, json.surname, json.hasPlusOne, json.plusOne);
    }
}
