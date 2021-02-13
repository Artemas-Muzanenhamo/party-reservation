package com.reservation.entry.web;

import com.reservation.entry.domain.Reservation;
import com.reservation.entry.exception.ReservationNotValidException;
import com.reservation.entry.json.ReservationJson;
import com.reservation.entry.service.ReservationMessageService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

import static com.reservation.entry.web.ReservationEndpoint.RESERVATION_URL;
import static java.net.URI.create;
import static java.util.Objects.nonNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static reactor.core.publisher.Mono.just;

@Component
public class ReservationHandler {
    private final ReservationMessageService reservationMessageServiceImpl;

    public ReservationHandler(ReservationMessageService reservationMessageServiceImpl) {
        this.reservationMessageServiceImpl = reservationMessageServiceImpl;
    }

    public Mono<ServerResponse> addReservation(ServerRequest request) {
        return request.bodyToMono(Reservation.class)
                .filter(hasSecret())
                .filter(isSecretEmpty())
                .flatMap(reservationMessageServiceImpl::bookReservation)
                .flatMap(ReservationHandler::generateBody);
    }

    private Mono<ServerResponse> badRequestResponse() {
        return badRequest()
                .bodyValue(new ReservationNotValidException("Reservation Secret is not set"));
    }

    private static Mono<ServerResponse> generateBody(ReservationJson reservationJson) {
        return created(create(RESERVATION_URL))
                .contentType(APPLICATION_JSON)
                .body(just(reservationJson), ReservationJson.class);
    }

    private Predicate<Reservation> hasSecret() {
        return reservation -> nonNull(reservation.getSecret());
    }

    private Predicate<Reservation> isSecretEmpty() {
        return reservation -> !reservation.getSecret().trim().isEmpty();
    }
}
