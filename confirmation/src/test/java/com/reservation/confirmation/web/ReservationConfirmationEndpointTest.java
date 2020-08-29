package com.reservation.confirmation.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ReservationConfirmationEndpoint.class)
class ReservationConfirmationEndpointTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Should return a flux list of reservations")
    void listOfReservations() {
        webTestClient
                .get()
                .uri("/party/reservations")
                .accept(TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

    }
}
