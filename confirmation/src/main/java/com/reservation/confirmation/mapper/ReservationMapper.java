package com.reservation.confirmation.mapper;

import com.reservation.confirmation.domain.Reservation;
import com.reservation.confirmation.domain.ReservationJson;

public class ReservationMapper {
    public static ReservationJson toReservationJsonFlux(Reservation reservation) {
        return new ReservationJson(
                reservation.getSecret(),
                reservation.getName(),
                reservation.getSurname(),
                reservation.getHasPlusOne(),
                reservation.getPlusOne()
        );








    }
}
