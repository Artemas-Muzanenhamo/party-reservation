package com.reservation.entry.web;

import com.reservation.entry.domain.ReservationJson;
import com.reservation.entry.dto.Reservation;
import com.reservation.entry.exception.ReservationNotValidException;
import com.reservation.entry.service.ReservationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class ReservationEndpoint {

    private final ReservationService reservationService;

    public ReservationEndpoint(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping(value = "/reservation", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<ReservationJson> bookReservation(@RequestBody Mono<ReservationJson> reservationJson) {
        Mono<Reservation> reservationMono = reservationJson
                .map(reservation -> new Reservation(
                        reservation.getSecret(),
                        reservation.getName(),
                        reservation.getSurname(),
                        reservation.getHasPlusOne(),
                        reservation.getPlusOne()
                )).onErrorMap(ex -> new ReservationNotValidException(ex.getMessage()));
        return reservationService.bookReservation(reservationMono);
    }
}
