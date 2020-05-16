package com.reservation.entry.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.entry.domain.ReservationMessageJson;
import com.reservation.entry.dto.Reservation;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReservationMessageServiceImplTest {
    private static final String NAME = "artemas";
    private static final String SURNAME = "muzanenhamo";
    private static final boolean HAS_PLUS_ONE = false;
    private static final int PLUS_ONE = 0;
    private static final String SECRET = "some secret word";
    private static final String TOPIC = "artemas-topic";

    @InjectMocks
    private ReservationMessageServiceImpl reservationMessageService;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @DisplayName("Should send a Reservation message")
    @Test
    void sendReservationTest() throws Exception {
        Reservation reservation = new Reservation(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);
        ReservationMessageJson reservationMessageJson = new ReservationMessageJson(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);
        String reservationMessageString = objectMapper.writeValueAsString(reservationMessageJson);
        ProducerRecord<String, String> reservationProducerRecord = new ProducerRecord<>(TOPIC, reservationMessageString);

        reservationMessageService.bookReservation(reservation);

        verify(kafkaTemplate).send(reservationProducerRecord);
    }
}