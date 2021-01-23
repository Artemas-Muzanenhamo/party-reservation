package com.reservation.entry.web;

import com.reservation.entry.domain.ReservationJson;
import com.reservation.entry.exception.ReservationNotValidException;
import com.reservation.entry.service.ReservationService;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static reactor.core.publisher.Mono.just;

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
    private ReservationService reservationServiceImpl;

    @Test
    @DisplayName("Should respond with a status CREATED when a reservation with valid details is entered")
    void successfulReservation() {
        ReservationJson reservationJson = new ReservationJson(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);
        Mono<ReservationJson> reservationJsonMono = just(reservationJson);
        given(reservationServiceImpl.bookReservation(any())).willReturn(reservationJsonMono);

        EntityExchangeResult<ReservationJson> reservationJsonEntityResult = webTestClient
                .post()
                .uri("/reservation")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .body(reservationJsonMono, ReservationJson.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(ReservationJson.class)
                .returnResult();

        ReservationJson responseBody = reservationJsonEntityResult.getResponseBody();

        assertThat(responseBody)
                .isNotNull()
                .isEqualToComparingFieldByField(reservationJson);


    }

    @Test
    @DisplayName("Should respond with a status BAD_REQUEST when a reservation is missing a secret")
    void errorOnReservationWithoutSecret() throws Exception {
        JSONObject response = new JSONObject();
        response.put("message", "Reservation Secret is not set");

        ReservationJson reservationJson = new ReservationJson(null, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);
        Mono<ReservationJson> reservationJsonMono = just(reservationJson);
        given(reservationServiceImpl.bookReservation(any())).willReturn(Mono.empty());

        EntityExchangeResult<ReservationNotValidException> reservationJsonEntityExchangeResult = webTestClient
                .post()
                .uri("/reservation")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .body(reservationJsonMono, ReservationJson.class)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(ReservationNotValidException.class)
                .returnResult();

        ReservationNotValidException responseBody = reservationJsonEntityExchangeResult.getResponseBody();

        assertThat(responseBody)
                .isNotNull()
                .hasMessage("Reservation Secret is not set");
    }

    @Test
    @DisplayName("Should respond with a status BAD_REQUEST when a reservation has an empty secret value")
    void errorOnReservationWithEmptySecretValue() {
        JSONObject response = new JSONObject();
        response.put("message", "Reservation Secret is not set");
        ReservationJson reservationJson = new ReservationJson("", NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);
        Mono<ReservationJson> reservationJsonMono = just(reservationJson);
        given(reservationServiceImpl.bookReservation(any())).willReturn(Mono.empty());

        EntityExchangeResult<ReservationNotValidException> reservationJsonEntityExchangeResult = webTestClient
                .post()
                .uri("/reservation")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .body(reservationJsonMono, ReservationJson.class)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(ReservationNotValidException.class)
                .returnResult();

        ReservationNotValidException responseBody = reservationJsonEntityExchangeResult.getResponseBody();

        assertThat(responseBody)
                .isNotNull()
                .hasMessage("Reservation Secret is not set");
    }
}