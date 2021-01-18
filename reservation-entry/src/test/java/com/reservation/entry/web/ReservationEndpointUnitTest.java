package com.reservation.entry.web;

import com.reservation.entry.domain.ReservationJson;
import com.reservation.entry.dto.Reservation;
import com.reservation.entry.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.BDDMockito.given;
import static reactor.core.publisher.Mono.just;

@ExtendWith(MockitoExtension.class)
class ReservationEndpointUnitTest {
    private static final String SECRET = "some secret party word";
    private static final String NAME = "artemas";
    private static final String SURNAME = "muzanenhamo";
    private static final boolean HAS_PLUS_ONE = false;
    private static final int PLUS_ONE = 0;
    private ReservationEndpoint reservationEndpoint;

    @Mock
    private ReservationService reservationServiceImpl;

    @BeforeEach
    void setUp() {
        reservationEndpoint = new ReservationEndpoint(reservationServiceImpl);
    }

    @Test
    @DisplayName("Should create a reservation when valid details are passed in")
    void createReservation() {
        ReservationJson reservationJson = new ReservationJson(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);
        Mono<ReservationJson> reservationJsonMono = just(reservationJson).switchIfEmpty(Mono.empty());
        Mono<Reservation> reservationMono = just(new Reservation(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE)).switchIfEmpty(Mono.empty());
        given(reservationServiceImpl.bookReservation(reservationMono)).willReturn(Mono.empty());

        // TODO: Get this to pass...
        Mono<Void> reservation = reservationEndpoint.bookReservation(reservationJsonMono);

        StepVerifier.create(reservation)
                .expectComplete()
                .verify();
    }
}
