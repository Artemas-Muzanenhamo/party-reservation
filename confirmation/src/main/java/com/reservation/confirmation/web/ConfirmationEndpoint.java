package com.reservation.confirmation.web;

import com.reservation.confirmation.domain.ReservationJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@RestController
@RequestMapping("/party")
public class ConfirmationEndpoint {

    @GetMapping(value = "/reservations", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<ReservationJson> retrieveReservations() {
        return Flux.empty();
    }
}
