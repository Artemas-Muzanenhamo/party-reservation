package com.reservation.entry.web;

import com.reservation.entry.domain.ReservationJson;
import com.reservation.entry.dto.Reservation;
import com.reservation.entry.exception.ReservationNotValidException;
import com.reservation.entry.service.ReservationService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static com.reservation.entry.web.ReservationEndpoint.RESERVATION_URL;
import static java.util.Objects.nonNull;
import static org.springframework.web.reactive.function.server.ServerResponse.created;

@Component
public class ReservationHandler {
    private final ReservationService reservationServiceImpl;

    public ReservationHandler(ReservationService reservationServiceImpl) {
        this.reservationServiceImpl = reservationServiceImpl;
    }

    public Mono<ServerResponse> addReservation(ServerRequest request) {
        Mono<Reservation> reservationMono = request.bodyToMono(ReservationJson.class)
                .filter(reservationJson -> nonNull(reservationJson.getSecret()))
                .map(reservationJson -> new Reservation(
                        reservationJson.getSecret(),
                        reservationJson.getName(),
                        reservationJson.getSurname(),
                        reservationJson.getHasPlusOne(),
                        reservationJson.getPlusOne()
                ))
                .onErrorMap(exception -> new ReservationNotValidException(exception.getMessage()));

        Mono<ReservationJson> reservationJsonMono = reservationServiceImpl.bookReservation(reservationMono);

        return created(URI.create(RESERVATION_URL)).body(reservationJsonMono, ReservationJson.class);
    }
}
