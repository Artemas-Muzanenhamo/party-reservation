package com.reservation.entry.service;

import com.reservation.entry.domain.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReservationMessageServiceTest {
    private static final String NAME = "artemas";
    private static final String SURNAME = "muzanenhamo";
    private static final boolean HAS_PLUS_ONE = false;
    private static final int PLUS_ONE = 0;
    private static final String SECRET = "some secret word";

    @InjectMocks
    private ReservationMessageService reservationMessageService;
    @Mock
    private KafkaTemplate<String, Reservation> kafkaTemplate;

    @Test
    @DisplayName("Sends a Reservation Message")
    void sendReservationTest() {
        Reservation reservation = new Reservation(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);

        Mono<Reservation> reservationMono = reservationMessageService.bookReservation(reservation);

        StepVerifier.create(reservationMono)
                .expectNext(reservation)
                .expectComplete()
                .verify();
        verify(kafkaTemplate).send(any(), eq(reservation));
    }
}
