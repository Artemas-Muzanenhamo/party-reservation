package com.reservation.confirmation.service;

import com.reservation.confirmation.domain.Reservation;
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
    private ReactiveKafkaConsumerTemplate<Object, Object> kafkaTemplate;
    @Mock
    private ReceiverRecord<Object, Object> record;

    @Test
    @DisplayName("Should return a Reservation Flux from the kafka service")
    void reservationFromKafka() {
        given(record.value()).willReturn(new Reservation(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE));
        given(kafkaTemplate.receive()).willReturn(Flux.just(record));

        Reservation reservation = new Reservation(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);

        Flux<Reservation> reservations = confirmationService.getReservations();

        StepVerifier.create(reservations)
                .expectNext(reservation)
                .thenCancel()
                .verify();
    }
}
