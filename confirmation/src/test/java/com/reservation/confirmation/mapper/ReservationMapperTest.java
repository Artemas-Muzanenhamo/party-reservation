package com.reservation.confirmation.mapper;

import com.reservation.confirmation.domain.Reservation;
import com.reservation.confirmation.domain.ReservationEvent;
import com.reservation.confirmation.exception.ReservationNotValidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.reservation.confirmation.mapper.ReservationMapper.toReservationEvent;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationMapperTest {

    private static final String SECRET = "Do Re Mi";
    private static final String NAME = "Artemas";
    private static final String SURNAME = "Prime";
    private static final boolean HAS_PLUS_ONE = false;
    private static final int PLUS_ONE = 1;
    private static final Reservation RESERVATION_DTO = new Reservation(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);
    private static final ReservationEvent RESERVATION_JSON = new ReservationEvent(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);

    @Test
    @DisplayName("Should convert a Reservation DTO to a ReservationJson")
    void convertToReservationJsonFlux() {
        ReservationEvent reservationEvent = toReservationEvent(RESERVATION_DTO);

        assertThat(reservationEvent).isNotNull();
        assertThat(reservationEvent.getSecret()).isEqualTo(SECRET);
        assertThat(reservationEvent.getName()).isEqualTo(NAME);
        assertThat(reservationEvent.getSurname()).isEqualTo(SURNAME);
        assertThat(reservationEvent.getHasPlusOne()).isEqualTo(HAS_PLUS_ONE);
        assertThat(reservationEvent.getPlusOne()).isEqualTo(PLUS_ONE);
    }

    @Test
    @DisplayName("Should thrown an exception when a null Reservation DTO is mapped")
    void nullReservationDTO() {
        assertThatThrownBy(() -> toReservationEvent(null))
                .isExactlyInstanceOf(ReservationNotValidException.class)
                .hasMessage("Reservation is not valid");
    }
}
