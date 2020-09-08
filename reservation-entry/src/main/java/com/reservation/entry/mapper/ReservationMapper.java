package com.reservation.entry.mapper;

import com.reservation.entry.domain.ReservationJson;
import com.reservation.entry.dto.Reservation;
import com.reservation.message.ReservationMessageJson;
import com.reservation.entry.exception.ReservationNotValidException;

import java.util.Objects;

import static java.lang.Boolean.FALSE;
import static java.util.Optional.ofNullable;

public class ReservationMapper {
    public static Reservation toReservationDTO(ReservationJson reservationJson) {
        return ofNullable(reservationJson)
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
