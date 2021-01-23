package com.reservation.entry.web;

import com.reservation.entry.domain.ReservationJson;
import com.reservation.entry.dto.Reservation;
import com.reservation.entry.exception.ReservationNotValidException;
import com.reservation.entry.service.ReservationService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static com.reservation.entry.web.ReservationEndpoint.RESERVATION_URL;
import static java.util.Objects.nonNull;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static reactor.core.publisher.Mono.just;

@Component
public class ReservationHandler {
    private final ReservationService reservationServiceImpl;

    public ReservationHandler(ReservationService reservationServiceImpl) {
        this.reservationServiceImpl = reservationServiceImpl;
    }

    public Mono<ServerResponse> addReservation(ServerRequest request) {
        Mono<Reservation> reservationMono = request.bodyToMono(Reservation.class)
                .filter(e -> nonNull(e.getSecret()))
                .onErrorStop()
                .onErrorMap(exception -> new ReservationNotValidException(exception.getMessage()));

        return reservationServiceImpl.bookReservation(reservationMono)
                .flatMap(reservationJson ->
                        created(URI.create(RESERVATION_URL))
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(just(reservationJson), ReservationJson.class))
                .switchIfEmpty(badRequest().bodyValue(new ReservationNotValidException("Something aint right here...")));
    }
}
