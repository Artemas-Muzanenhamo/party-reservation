package com.party.entry.reservationentry.mapper;

import com.party.entry.reservationentry.domain.ReservationJson;
import com.party.entry.reservationentry.dto.Reservation;
import com.party.entry.reservationentry.domain.ReservationMessageJson;
import com.party.entry.reservationentry.exception.ReservationNotValidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.party.entry.reservationentry.mapper.ReservationMapper.toReservationDTO;
import static com.party.entry.reservationentry.mapper.ReservationMapper.toReservationMessageJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReservationMapperTest {

    private static final String NAME = "artemas";
    private static final String SURNAME = "muzanenhamo";
    private static final boolean HAS_PLUS_ONE = false;
    private static final int PLUS_ONE = 0;
    private static final String SECRET = "some secret word";

    @Test
    @DisplayName("Should map ReservationJson to ReservationDTO")
    void mapReservationJsonToDTO() {
        ReservationJson reservationJson = new ReservationJson(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);

        Reservation reservation = toReservationDTO(reservationJson);

        assertThat(reservation).isNotNull();
        assertThat(reservation.getSecret()).isEqualTo(SECRET);
        assertThat(reservation.getName()).isEqualTo(NAME);
        assertThat(reservation.getSurname()).isEqualTo(SURNAME);
        assertThat(reservation.getHasPlusOne()).isEqualTo(HAS_PLUS_ONE);
        assertThat(reservation.getPlusOne()).isEqualTo(PLUS_ONE);
    }

    @Test
    @DisplayName("Should throw a ReservationNotValidException when a secret is not supplied")
    void throwExceptionWhenSecretIsNull() {
        ReservationJson reservationJson = new ReservationJson(null, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);

        assertThrows(ReservationNotValidException.class, () -> toReservationDTO(reservationJson));
    }

    @Test
    @DisplayName("Should throw a ReservationNotValidException when a secret is supplied is an empty value")
    void throwExceptionWhenSecretIsEmpty() {
        ReservationJson reservationJson = new ReservationJson("", NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);

        assertThrows(ReservationNotValidException.class, () -> toReservationDTO(reservationJson));
    }

    @Test
    @DisplayName("Should throw a ReservationNotValidException ReservationJson is null")
    void throwExceptionWhenReservationIsNull() {
        assertThrows(ReservationNotValidException.class, () -> toReservationDTO(null));
    }

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