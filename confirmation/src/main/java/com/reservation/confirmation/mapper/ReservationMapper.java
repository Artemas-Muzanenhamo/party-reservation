package com.reservation.confirmation.mapper;

import com.reservation.confirmation.domain.Reservation;
import com.reservation.confirmation.domain.ReservationJson;
import reactor.core.publisher.Flux;

public class ReservationMapper {
    public static Flux<ReservationJson> toReservationJsonFlux(Flux<Reservation> reservations) {
        return reservations
                .map(reservation -> new ReservationJson(
                        reservation.getSecret(),
                        reservation.getName(),
                        reservation.getSurname(),
                        reservation.getHasPlusOne(),
                        reservation.getPlusOne()
                ))
                .doOnComplete(() -> { });
    }
}
