package com.party.entry.reservationentry.mapper;

import com.party.entry.reservationentry.domain.ReservationJson;
import com.party.entry.reservationentry.dto.Reservation;
import com.party.entry.reservationentry.entity.ReservationMessageEntity;
import com.party.entry.reservationentry.exception.ReservationNotValidException;

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

    public static ReservationMessageEntity toReservationMessageEntity(Reservation reservation) {
        return ofNullable(reservation)
                .map(reservationDTO -> new ReservationMessageEntity(
                        reservationDTO.getSecret(),
                        reservationDTO.getName(),
                        reservationDTO.getSurname(),
                        reservationDTO.getHasPlusOne(),
                        reservationDTO.getPlusOne()
                ))
                .orElse(new ReservationMessageEntity());
    }
}
