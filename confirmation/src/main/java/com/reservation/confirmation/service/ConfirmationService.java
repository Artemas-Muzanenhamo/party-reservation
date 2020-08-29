package com.reservation.confirmation.service;

import com.reservation.confirmation.domain.Reservation;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public interface ConfirmationService {
    Flux<Reservation> getReservations();
}
