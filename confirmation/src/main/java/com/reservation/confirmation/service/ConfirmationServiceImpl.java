package com.reservation.confirmation.service;

import com.reservation.confirmation.domain.Reservation;
import com.reservation.confirmation.exception.ReservationNotValidException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {
    @Override
    public Flux<Reservation> getReservations() {
        return Flux.error(() -> new ReservationNotValidException("something went wrong"));
    }
}
