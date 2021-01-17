package com.reservation.entry.kafka;

import com.reservation.entry.dto.Reservation;
import reactor.core.publisher.Mono;

public interface ReservationMessageService {
    Mono<Void> bookReservation(Reservation reservation);
}
