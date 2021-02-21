package com.reservation.entry.mapper;

import com.reservation.entry.domain.Reservation;
import com.reservation.message.ReservationMessageJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.reservation.entry.mapper.ReservationMapper.toReservationMessageJson;
import static org.assertj.core.api.Assertions.assertThat;

class ReservationMapperTest {

    private static final String NAME = "artemas";
    private static final String SURNAME = "muzanenhamo";
    private static final boolean HAS_PLUS_ONE = false;
    private static final int PLUS_ONE = 0;
    private static final String SECRET = "some secret word";

    @Test
    @DisplayName("Should convert ReservationDTO to ReservationMessageJson")
    void mapReservationMessageEntity() {
        Reservation reservation = new Reservation(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);

        ReservationMessageJson json = toReservationMessageJson(reservation);

        assertThat(json).isNotNull();
        assertThat(json.getSecret()).isEqualTo(SECRET);
        assertThat(json.getName()).isEqualTo(NAME);
        assertThat(json.getSurname()).isEqualTo(SURNAME);
        assertThat(json.getHasPlusOne()).isEqualTo(HAS_PLUS_ONE);
        assertThat(json.getPlusOne()).isEqualTo(PLUS_ONE);
    }

    @Test
    @DisplayName("Should return an empty ReservationMessageJson when ReservationDTO is null")
    void mapNullReservationToEmptyReservationMessageJson() {
        ReservationMessageJson json = toReservationMessageJson(null);

        assertThat(json).isNotNull();
    }
}
