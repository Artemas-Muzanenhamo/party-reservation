package com.reservation.entry.service;

import com.reservation.entry.dto.Reservation;
import com.reservation.entry.kafka.ReservationMessageService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationMessageService reservationMessageService;

    public ReservationServiceImpl(ReservationMessageService reservationMessageService) {
        this.reservationMessageService = reservationMessageService;
    }

    @Override
    public Mono<Void> bookReservation(Mono<Reservation> reservation) {
        return reservationMessageService.bookReservation(new Reservation());
    }
}
