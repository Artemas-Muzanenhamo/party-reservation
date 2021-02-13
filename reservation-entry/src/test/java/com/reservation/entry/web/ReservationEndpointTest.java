package com.reservation.entry.web;

import com.reservation.entry.domain.Reservation;
import com.reservation.entry.json.ReservationJson;
import com.reservation.entry.service.ReservationMessageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static reactor.core.publisher.Mono.just;
import static reactor.test.StepVerifier.create;

@ExtendWith(SpringExtension.class)
@WebFluxTest({ReservationEndpoint.class, ReservationHandler.class})
class ReservationEndpointTest {
    private static final String NAME = "artemas";
    private static final String SURNAME = "muzanenhamo";
    private static final boolean HAS_PLUS_ONE = false;
    private static final int PLUS_ONE = 0;
    private static final String SECRET = "some secret word";

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private ReservationMessageService reservationMessageServiceImpl;

    @Test
    @DisplayName("Should respond with a status CREATED when a reservation with valid details is entered")
    void successfulReservation() {
        ReservationJson reservationJson = new ReservationJson(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);
        Mono<ReservationJson> reservationJsonMono = just(reservationJson);
        Reservation reservation = new Reservation(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);
        given(reservationMessageServiceImpl.bookReservation(reservation)).willReturn(reservationJsonMono);

        FluxExchangeResult<ReservationJson> reservationJsonFluxExchangeResult = webTestClient
                .post()
                .uri("/reservation")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .body(reservationJsonMono, ReservationJson.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .returnResult(ReservationJson.class);

        create(reservationJsonFluxExchangeResult.getResponseBody())
                .expectNext(reservationJson)
                .expectComplete()
                .verify();
    }

    // TODO
//    @Test
//    @DisplayName("Should respond with a status BAD_REQUEST when a reservation json is empty or null")
//    void errorOnNullOrEmptyReservation() {
//        given(reservationMessageServiceImpl.bookReservation(any())).willReturn(empty());
//
//        FluxExchangeResult<ReservationNotValidException> exchangeResult = webTestClient
//                .post()
//                .uri("/reservation")
//                .accept(APPLICATION_JSON)
//                .contentType(APPLICATION_JSON)
//                .body(empty(), ReservationJson.class)
//                .exchange()
//                .expectStatus()
//                .is5xxServerError()
//                .returnResult(ReservationNotValidException.class);
//
//        verifyNoInteractions(reservationMessageServiceImpl);
//        create(exchangeResult.getResponseBody().log())
//                .expectNext(new ReservationNotValidException("The Reservation You Gave Is Not Valid!"))
//                .verifyComplete();
//    }
}