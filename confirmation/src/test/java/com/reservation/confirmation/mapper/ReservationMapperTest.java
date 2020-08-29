package com.reservation.confirmation.mapper;

import com.reservation.confirmation.domain.Reservation;
import com.reservation.confirmation.domain.ReservationJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static com.reservation.confirmation.mapper.ReservationMapper.toReservationJsonFlux;
import static reactor.core.publisher.Flux.just;

class ReservationMapperTest {

    private static final Reservation RESERVATION_DTO = new Reservation("", "", "", false, 1);
    private static final ReservationJson RESERVATION_JSON = new ReservationJson("", "", "", false, 1);

    @Test
    @DisplayName("Should convert a Flux<Reservation> DTO to a Flux<ReservationJson>")
    void convertToReservationJsonFlux() {
        Flux<Reservation> reservations = just(RESERVATION_DTO);

        Flux<ReservationJson> reservationJsonFlux = toReservationJsonFlux(reservations);

        StepVerifier.create(reservationJsonFlux)
                .expectNext(RESERVATION_JSON)
                .thenCancel()
                .verify();
    }
}
