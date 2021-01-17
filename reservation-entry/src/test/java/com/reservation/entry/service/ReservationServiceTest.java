package com.reservation.entry.service;

import com.reservation.entry.dto.Reservation;
import com.reservation.entry.kafka.ReservationMessageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.just;

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
        Mono<Reservation> reservationMono = just(reservation);
        doNothing().when(reservationMessageService).bookReservation(reservation);

        reservationServiceImpl.bookReservation(reservationMono);

        verify(reservationMessageService).bookReservation(reservation);
    }
}