package com.reservation.entry.kafka;

import com.reservation.entry.domain.ReservationJson;
import com.reservation.entry.dto.Reservation;
import reactor.core.publisher.Mono;

public interface ReservationMessageService {
    Mono<ReservationJson> bookReservation(Reservation reservation);
}
