package com.reservation.entry.service;

import com.reservation.entry.dto.Reservation;
import com.reservation.entry.kafka.ReservationMessageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @InjectMocks
    private ReservationServiceImpl reservationServiceImpl;
    @Mock
    private ReservationMessageService reservationMessageService;

    @Test
    @DisplayName("Should add reservation")
    void addReservation() {
        Reservation reservation = new Reservation();
        doNothing().when(reservationMessageService).bookReservation(reservation);

        reservationServiceImpl.bookReservation(reservation);

        verify(reservationMessageService).bookReservation(reservation);
    }
}