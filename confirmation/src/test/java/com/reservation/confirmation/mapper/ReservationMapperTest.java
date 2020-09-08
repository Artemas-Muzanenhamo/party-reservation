package com.reservation.confirmation.mapper;

import com.reservation.confirmation.domain.Reservation;
import com.reservation.confirmation.domain.ReservationJson;
import com.reservation.confirmation.exception.ReservationNotValidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.reservation.confirmation.mapper.ReservationMapper.toReservationJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        ReservationJson reservationJson = toReservationJson(RESERVATION_DTO);

        assertThat(reservationJson).isNotNull();
        assertThat(reservationJson.secret).isEqualTo(SECRET);
        assertThat(reservationJson.name).isEqualTo(NAME);
        assertThat(reservationJson.surname).isEqualTo(SURNAME);
        assertThat(reservationJson.hasPlusOne).isEqualTo(HAS_PLUS_ONE);
        assertThat(reservationJson.plusOne).isEqualTo(PLUS_ONE);
    }

    @Test
    @DisplayName("Should thrown an exception when a null Reservation DTO is mapped")
    void nullReservationDTO() {
        assertThatThrownBy(() -> toReservationJson(null))
                .isExactlyInstanceOf(ReservationNotValidException.class)
                .hasMessage("Reservation is not valid");
    }
}
