package com.reservation.confirmation.mapper;

import com.reservation.confirmation.domain.Reservation;
import com.reservation.confirmation.domain.ReservationJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.reservation.confirmation.mapper.ReservationMapper.toReservationJsonFlux;
import static org.assertj.core.api.Assertions.assertThat;

class ReservationMapperTest {

    private static final String SECRET = "Do Re Mi";
    private static final String NAME = "Artemas";
    private static final String SURNAME = "Prime";
    private static final boolean HAS_PLUS_ONE = false;
    private static final int PLUS_ONE = 1;
    private static final Reservation RESERVATION_DTO = new Reservation(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);
    private static final ReservationJson RESERVATION_JSON = new ReservationJson(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);

    @Test
    @DisplayName("Should convert a Reservation DTO to a ReservationJson")
    void convertToReservationJsonFlux() {
        ReservationJson reservationJson = toReservationJsonFlux(RESERVATION_DTO);

        assertThat(reservationJson).isNotNull();
    }
}
