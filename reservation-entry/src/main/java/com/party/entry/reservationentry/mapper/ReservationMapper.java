package com.party.entry.reservationentry.mapper;

import com.party.entry.reservationentry.domain.ReservationJson;
import com.party.entry.reservationentry.dto.Reservation;
import com.party.entry.reservationentry.exception.ReservationNotValidException;

import java.util.Objects;
import java.util.Optional;

import static java.lang.Boolean.FALSE;

public class ReservationMapper {
    public static Reservation toReservationDTO(ReservationJson reservationJson) {
        return Optional.ofNullable(reservationJson)
                .filter(json -> Objects.nonNull(json.getSecret()))
                .filter(json -> FALSE.equals(json.getSecret().isEmpty()))
                .map(json -> new Reservation(
                        json.getSecret(),
                        json.getName(),
                        json.getSurname(),
                        json.getHasPlusOne(),
                        json.getPlusOne()
                ))
                .orElseThrow(ReservationNotValidException::new);
    }
}
