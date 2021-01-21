package com.reservation.entry.web;

import com.reservation.entry.domain.ReservationJson;
import com.reservation.entry.service.ReservationService;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static reactor.core.publisher.Mono.just;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ReservationEndpoint.class)
class ReservationEndpointTest {
    private static final String NAME = "artemas";
    private static final String SURNAME = "muzanenhamo";
    private static final boolean HAS_PLUS_ONE = false;
    private static final int PLUS_ONE = 0;
    private static final String SECRET = "some secret word";

    @Autowired
    private WebTestClient webTestClient;
    private MockMvc mockMvc;
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
                .body(just(reservationJson), ReservationJson.class)
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
        JSONObject json = new JSONObject();
        json.appendField("name", NAME);
        json.appendField("surname", SURNAME);
        json.appendField("hasPlusOne", HAS_PLUS_ONE);
        json.appendField("plusOne", PLUS_ONE);
        JSONObject response = new JSONObject();
        response.put("message", "Reservation Secret is not set");

        mockMvc.perform(post("/reservation")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(json.toJSONString())
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(response.toJSONString()));
    }

    @Test
    @DisplayName("Should respond with a status BAD_REQUEST when a reservation has an empty secret value")
    void errorOnReservationWithEmptySecretValue() throws Exception {
        JSONObject json = new JSONObject();
        json.put("secret", "");
        json.appendField("name", NAME);
        json.appendField("surname", SURNAME);
        json.appendField("hasPlusOne", HAS_PLUS_ONE);
        json.appendField("plusOne", PLUS_ONE);
        JSONObject response = new JSONObject();
        response.put("message", "Reservation Secret is not set");

        mockMvc.perform(post("/reservation")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(json.toJSONString())
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(response.toJSONString()));
    }
}