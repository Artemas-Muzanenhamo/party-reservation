package com.reservation.confirmation.service;

import com.reservation.confirmation.domain.Reservation;
import com.reservation.confirmation.exception.ReservationNotValidException;
import com.reservation.message.ReservationMessageJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.ReceiverRecord;
import reactor.test.StepVerifier;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ConfirmationServiceTest {
    private static final String SECRET = "my-secret";
    private static final String NAME = "artemas";
    private static final String SURNAME = "prime";
    private static final boolean HAS_PLUS_ONE = false;
    private static final int PLUS_ONE = 0;

    @InjectMocks
    private ConfirmationServiceImpl confirmationService;
    @Mock
    private ReactiveKafkaConsumerTemplate<String, ReservationMessageJson> kafkaTemplate;
    @Mock
    private ReceiverRecord<String, ReservationMessageJson> record;

    @Test
    @DisplayName("Should return a Reservation Flux from the kafka service")
    void reservationFromKafka() {
        ReservationMessageJson reservationMessageJson = new ReservationMessageJson(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);
        given(record.value()).willReturn(reservationMessageJson);
        given(kafkaTemplate.receive()).willReturn(Flux.just(record));
        Reservation reservation = new Reservation(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);

        Flux<Reservation> reservations = confirmationService.getReservations();

        StepVerifier.create(reservations)
                .expectNext(reservation)
                .thenCancel()
                .verify();
    }

    @Test
    @DisplayName("Should throw a ReservationNotValidException when the reservation message value is not valid")
    void invalidReservationValueFromKafka() {
        given(record.value()).willReturn(null);
        given(kafkaTemplate.receive()).willReturn(Flux.just(record));

        Flux<Reservation> reservations = confirmationService.getReservations();

        StepVerifier.create(reservations)
                .expectError(ReservationNotValidException.class)
                .verify();
    }
}
