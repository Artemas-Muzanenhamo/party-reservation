package com.reservation.confirmation.service;

import com.reservation.confirmation.domain.Reservation;
import reactor.core.publisher.Flux;

public interface ConfirmationService {
    Flux<Reservation> getReservations();
}
