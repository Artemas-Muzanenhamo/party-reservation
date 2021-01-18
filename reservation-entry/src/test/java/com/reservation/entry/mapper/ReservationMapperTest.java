package com.reservation.entry.mapper;

import com.reservation.entry.domain.ReservationJson;
import com.reservation.entry.dto.Reservation;
import com.reservation.entry.exception.ReservationNotValidException;
import com.reservation.message.ReservationMessageJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.reservation.entry.mapper.ReservationMapper.toReservationDTO;
import static com.reservation.entry.mapper.ReservationMapper.toReservationMessageJson;
import static org.assertj.core.api.Assertions.assertThat;
import static reactor.core.publisher.Mono.just;
import static reactor.test.StepVerifier.create;

class ReservationMapperTest {

    private static final String NAME = "artemas";
    private static final String SURNAME = "muzanenhamo";
    private static final boolean HAS_PLUS_ONE = false;
    private static final int PLUS_ONE = 0;
    private static final String SECRET = "some secret word";

    @Test
    @DisplayName("Should map ReservationJson to ReservationDTO")
    void mapReservationJsonToDTO() {
        Reservation reservationDto = new Reservation(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);
        Mono<ReservationJson> reservationJsonMono = just(new ReservationJson(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE));

        Mono<Reservation> reservationDtoMono = toReservationDTO(reservationJsonMono);

        create(reservationDtoMono)
                .expectNext(reservationDto)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Should complete the sequence when a secret is not supplied")
    void throwExceptionWhenSecretIsNull() {
        Mono<ReservationJson> reservationJsonMono = just(new ReservationJson(null, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE));
        Mono<Reservation> reservationDtoMono = toReservationDTO(reservationJsonMono);

        StepVerifier.create(reservationDtoMono)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Should complete the sequence when a secret supplied is an empty value")
    void throwExceptionWhenSecretIsEmpty() {
        Mono<ReservationJson> reservationJsonMono = just(new ReservationJson("", NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE));
        Mono<Reservation> reservationDtoMono = toReservationDTO(reservationJsonMono);

        StepVerifier.create(reservationDtoMono)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Should throw a ReservationNotValidException ReservationJson is null")
    void throwExceptionWhenReservationIsNull() {
        Mono<Reservation> reservationMono = toReservationDTO(Mono.error(Throwable::new));

        StepVerifier.create(reservationMono)
                .expectError(ReservationNotValidException.class)
                .verify();
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
