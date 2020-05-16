package com.reservation.entry.service;

import com.reservation.entry.dto.Reservation;
import com.reservation.entry.kafka.ReservationMessageService;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationMessageService reservationMessageService;

    public ReservationServiceImpl(ReservationMessageService reservationMessageService) {
        this.reservationMessageService = reservationMessageService;
    }

    @Override
    public void bookReservation(Reservation reservation) {
        reservationMessageService.bookReservation(reservation);
    }
}
