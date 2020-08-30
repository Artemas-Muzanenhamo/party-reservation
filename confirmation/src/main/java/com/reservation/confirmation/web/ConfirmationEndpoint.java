package com.reservation.confirmation.web;

import com.reservation.confirmation.domain.Reservation;
import com.reservation.confirmation.domain.ReservationJson;
import com.reservation.confirmation.service.ConfirmationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static com.reservation.confirmation.mapper.ReservationMapper.toReservationJsonFlux;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@RestController
public class ConfirmationEndpoint {

    private final ConfirmationService confirmationService;

    public ConfirmationEndpoint(ConfirmationService confirmationService) {
        this.confirmationService = confirmationService;
    }

    @GetMapping(value = "/party/reservations", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<ReservationJson> retrieveReservations() {
        Flux<Reservation> reservations = confirmationService.getReservations();
        return toReservationJsonFlux(reservations);
    }
}
