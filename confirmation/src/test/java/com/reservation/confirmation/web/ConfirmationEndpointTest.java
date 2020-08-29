package com.reservation.confirmation.web;

import com.reservation.confirmation.domain.ReservationJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ConfirmationEndpoint.class)
class ConfirmationEndpointTest {

    private static final ReservationJson FIRST_RESERVATION = new ReservationJson("", "", "", false, 1);
    private static final ReservationJson SECOND_RESERVATION = new ReservationJson("", "", "", false, 1);

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Should return a flux list of reservations")
    void listOfReservations() {
        FluxExchangeResult<ReservationJson> reservationFluxExchangeResult = webTestClient
                .get()
                .uri("/party/reservations")
                .accept(TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(ReservationJson.class);

        Flux<ReservationJson> responseBody = reservationFluxExchangeResult.getResponseBody();

        StepVerifier.create(responseBody)
                .expectNext(FIRST_RESERVATION, SECOND_RESERVATION)
                .thenCancel()
                .verify();
    }
}
