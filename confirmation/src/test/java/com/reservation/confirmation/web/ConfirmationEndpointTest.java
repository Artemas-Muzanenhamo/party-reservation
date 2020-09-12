package com.reservation.confirmation.web;

import com.reservation.confirmation.domain.Reservation;
import com.reservation.confirmation.domain.ReservationEvent;
import com.reservation.confirmation.service.ConfirmationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static reactor.core.publisher.Flux.just;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ConfirmationEndpoint.class)
class ConfirmationEndpointTest {
    private static final String SECRET = "arty's secret";
    private static final String NAME = "Artemas";
    private static final String SURNAME = "THE GREAT";
    private static final boolean HAS_PLUS_ONE = true;
    private static final int PLUS_ONE = 1;
    private static final ReservationEvent FIRST_RESERVATION = new ReservationEvent(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);
    private static final ReservationEvent SECOND_RESERVATION = new ReservationEvent(SECRET, NAME, SURNAME, false, 0);
    private static final Reservation FIRST_RESERVATION_DTO = new Reservation(SECRET, NAME, SURNAME, HAS_PLUS_ONE, 1);
    private static final Reservation SECOND_RESERVATION_DTO = new Reservation(SECRET, NAME, SURNAME, false, 0);
    private static final String MESSAGE = "This Reservation is invalid";

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ConfirmationService confirmationService;

    @Test
    @DisplayName("Should return a flux list of reservations events")
    void listOfReservations() {
        given(confirmationService.getReservations()).willReturn(just(FIRST_RESERVATION_DTO, SECOND_RESERVATION_DTO));

        FluxExchangeResult<ReservationEvent> reservationFluxExchangeResult = webTestClient
                .get()
                .uri("/party/reservations")
                .accept(TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(ReservationEvent.class);

        Flux<ReservationEvent> responseBody = reservationFluxExchangeResult.getResponseBody();

        StepVerifier.create(responseBody)
                .expectNext(FIRST_RESERVATION, SECOND_RESERVATION)
                .thenCancel()
                .verify();
    }

    @Test
    @DisplayName("Should throw 400 BAD REQUEST when the server fails")
    void whenServerFails() {
        given(confirmationService.getReservations()).willReturn(Flux.empty());

        FluxExchangeResult<ReservationEvent> reservationFluxExchangeResult = webTestClient
                .get()
                .uri("/party/reservations")
                .accept(TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .returnResult(ReservationEvent.class);

        Flux<ReservationEvent> responseBody = reservationFluxExchangeResult.getResponseBody();

        StepVerifier.create(responseBody)
                .expectNext()
                .thenCancel()
                .verify();
    }
}
