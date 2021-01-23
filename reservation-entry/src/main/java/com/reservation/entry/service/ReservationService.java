package com.reservation.entry.service;

import com.reservation.entry.domain.ReservationJson;
import com.reservation.entry.dto.Reservation;
import reactor.core.publisher.Mono;

public interface ReservationService {
    Mono<ReservationJson> bookReservation(Mono<Reservation> reservationMono);
}
