package com.reservation.entry.mapper;

import com.reservation.entry.json.ReservationJson;
import com.reservation.entry.domain.Reservation;
import com.reservation.entry.exception.ReservationNotValidException;
import com.reservation.message.ReservationMessageJson;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

import static java.lang.Boolean.FALSE;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

public class ReservationMapper {
    private ReservationMapper() {}

    public static Mono<Reservation> toReservationDTO(Mono<ReservationJson> reservationJson) {

        return reservationJson
                .filter(ReservationMapper::hasReservationSecret)
                .filter(ReservationMapper::isSecretEmpty)
                .map(ReservationMapper::toReservation)
                .onErrorMap(exception -> new ReservationNotValidException(exception.getMessage()));
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

    @NotNull
    private static Reservation toReservation(ReservationJson json) {
        return new Reservation(
                json.getSecret(),
                json.getName(),
                json.getSurname(),
                json.getHasPlusOne(),
                json.getPlusOne()
        );
    }

    private static boolean isSecretEmpty(ReservationJson json) {
        return FALSE.equals(json.getSecret().isEmpty());
    }

    @NotNull
    private static boolean hasReservationSecret(ReservationJson json) {
        return nonNull(json.getSecret());
    }
}
