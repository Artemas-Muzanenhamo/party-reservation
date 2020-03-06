package com.party.entry.reservationentry.kafka;

import com.party.entry.reservationentry.dto.Reservation;
import org.springframework.stereotype.Service;

@Service
public class ReservationMessageServiceImpl implements ReservationMessageService {
    @Override
    public void bookReservation(Reservation reservation) {
    }
}
