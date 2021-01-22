package com.reservation.entry.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ReservationEndpoint {
    static final String RESERVATION_URL = "/reservation";

    @Bean
    RouterFunction<ServerResponse> reservationRoutes(ReservationHandler reservationHandler) {
        return route(
                POST(RESERVATION_URL),
                reservationHandler::addReservation
        );
    }
}
