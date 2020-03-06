package com.party.entry.reservationentry.service;

import com.party.entry.reservationentry.dto.Reservation;
import com.party.entry.reservationentry.kafka.ReservationMessageService;
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
