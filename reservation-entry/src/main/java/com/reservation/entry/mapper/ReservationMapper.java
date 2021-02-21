package com.reservation.entry.mapper;

import com.reservation.entry.domain.Reservation;
import com.reservation.message.ReservationMessageJson;

import static java.util.Optional.ofNullable;

public class ReservationMapper {
    private ReservationMapper() {}

    public static ReservationMessageJson toReservationMessageJson(Reservation reservation) {
        return ofNullable(reservation)
                .map(reservationDTO -> new ReservationMessageJson(
                        reservationDTO.getSecret(),
                        reservationDTO.getName(),
                        reservationDTO.getSurname(),
                        reservationDTO.getHasPlusOne(),
                        reservationDTO.getPlusOne()
                ))
                .orElse(new ReservationMessageJson());
    }
}
