package com.reservation.confirmation.mapper;

import com.reservation.confirmation.domain.Reservation;
import com.reservation.confirmation.domain.ReservationEvent;
import com.reservation.confirmation.exception.ReservationNotValidException;

import java.util.Optional;

public class ReservationMapper {
    public static ReservationEvent toReservationEvent(Reservation reservation) {
        return Optional.ofNullable(reservation)
                .map(partyReservation -> new ReservationEvent(
                        partyReservation.getSecret(),
                        partyReservation.getName(),
                        partyReservation.getSurname(),
                        partyReservation.getHasPlusOne(),
                        partyReservation.getPlusOne()
                ))
                .orElseThrow(() -> new ReservationNotValidException("Reservation is not valid"));
    }
}
