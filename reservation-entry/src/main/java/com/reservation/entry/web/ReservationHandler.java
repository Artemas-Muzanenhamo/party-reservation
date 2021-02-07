package com.reservation.entry.web;

import com.reservation.entry.domain.ReservationJson;
import com.reservation.entry.dto.Reservation;
import com.reservation.entry.exception.ReservationNotValidException;
import com.reservation.entry.service.ReservationService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.reservation.entry.web.ReservationEndpoint.RESERVATION_URL;
import static java.net.URI.create;
import static java.util.Objects.nonNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
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
                .filter(ReservationHandler::hasSecret);

        return reservationServiceImpl.bookReservation(reservationMono)
                .flatMap(ReservationHandler::generateBody)
                .switchIfEmpty(badRequestResponse());
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

    private static boolean hasSecret(Reservation reservation) {
        return nonNull(reservation.getSecret());
    }
}
