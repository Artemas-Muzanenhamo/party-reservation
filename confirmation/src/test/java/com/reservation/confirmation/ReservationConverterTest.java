package com.reservation.confirmation;

import com.reservation.confirmation.domain.Reservation;
import com.reservation.message.ReservationMessageJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.reservation.confirmation.ReservationConverter.toReservation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ReservationConverterTest {

    private static final int PLUS_ONE = 0;
    private static final boolean HAS_PLUS_ONE = false;
    private static final String SURNAME = "prime";
    private static final String NAME = "arty";
    private static final String SECRET = "my-secret";

    @Test
    @DisplayName("Should convert ReservationMessageJson to Reservation DTO")
    void toReservationDto() {
        ReservationMessageJson reservationMessageJson = new ReservationMessageJson(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);

        Reservation reservation = toReservation(reservationMessageJson);

        assertThat(reservation).isNotNull();
        assertThat(reservation.getSecret()).isEqualTo(SECRET);
        assertThat(reservation.getName()).isEqualTo(NAME);
        assertThat(reservation.getSurname()).isEqualTo(SURNAME);
        assertThat(reservation.getHasPlusOne()).isEqualTo(HAS_PLUS_ONE);
        assertThat(reservation.getPlusOne()).isEqualTo(PLUS_ONE);
    }
}
