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
    private static final String SECRET = "my-secret";
    private static final String NAME = "artemas";
    private static final String SURNAME = "prime";
    private static final boolean HAS_PLUS_ONE = false;
    private static final int PLUS_ONE = 0;

    @InjectMocks
    private ReservationServiceImpl reservationServiceImpl;
    @Mock
    private ReservationMessageService reservationMessageService;

    @Test
    @DisplayName("Should add reservation")
    void addReservation() {
        Reservation reservation = new Reservation(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);
        Mono<Reservation> reservationDtoMono = just(reservation);
        ReservationJson reservationJson = new ReservationJson(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);
        Mono<ReservationJson> reservationJsonMono = just(reservationJson);
        given(reservationMessageService.bookReservation(reservation)).willReturn(reservationJsonMono);

        Mono<ReservationJson> reservationMono = reservationServiceImpl.bookReservation(reservationDtoMono);

        StepVerifier.create(reservationMono)
                .expectNext(reservationJson)
                .expectComplete()
                .verify();
        verify(reservationMessageService).bookReservation(reservation);
    }
}