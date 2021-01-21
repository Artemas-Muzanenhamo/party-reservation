package com.reservation.entry.service;

import com.reservation.entry.domain.ReservationJson;
import com.reservation.entry.dto.Reservation;
import com.reservation.entry.kafka.ReservationMessageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.BDDMockito.given;
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
        Mono<Reservation> reservationDtoMono = just(reservation);
        given(reservationMessageService.bookReservation(reservation)).willReturn(Mono.empty());

        Mono<ReservationJson> voidMono = reservationServiceImpl.bookReservation(reservationDtoMono);

        StepVerifier.create(voidMono)
                .expectComplete()
                .verify();
        verify(reservationMessageService).bookReservation(reservation);
    }
}