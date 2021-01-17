package com.reservation.entry.service;

import com.reservation.entry.dto.Reservation;
import reactor.core.publisher.Mono;

public interface ReservationService {
    Mono<Void> bookReservation(Mono<Reservation> reservation);
}
