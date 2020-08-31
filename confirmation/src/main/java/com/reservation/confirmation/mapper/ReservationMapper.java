package com.reservation.confirmation.mapper;

import com.reservation.confirmation.domain.Reservation;
import com.reservation.confirmation.domain.ReservationJson;
import com.reservation.confirmation.exception.ReservationNotValidException;

import java.util.Optional;

public class ReservationMapper {
    public static ReservationJson toReservationJsonFlux(Reservation reservation) {
        return Optional.ofNullable(reservation)
                .map(partyReservation -> new ReservationJson(
                        partyReservation.getSecret(),
                        partyReservation.getName(),
                        partyReservation.getSurname(),
                        partyReservation.getHasPlusOne(),
                        partyReservation.getPlusOne()
                ))
                .orElseThrow(() -> new ReservationNotValidException("Reservation is not valid"));
    }
}
