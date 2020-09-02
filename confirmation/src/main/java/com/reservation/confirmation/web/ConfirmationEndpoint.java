package com.reservation.confirmation.web;

import com.reservation.confirmation.domain.ReservationJson;
import com.reservation.confirmation.exception.ReservationNotValidException;
import com.reservation.confirmation.mapper.ReservationMapper;
import com.reservation.confirmation.service.ConfirmationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@RestController
public class ConfirmationEndpoint {

    private final ConfirmationService confirmationService;

    public ConfirmationEndpoint(ConfirmationService confirmationService) {
        this.confirmationService = confirmationService;
    }

    @GetMapping(value = "/party/reservations", produces = TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<ReservationJson> retrieveReservations() {
        return confirmationService.getReservations()
                .map(ReservationMapper::toReservationJsonFlux)
                .switchIfEmpty(Flux.error(() -> new ReservationNotValidException("something went wrong")));
    }
}
