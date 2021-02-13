package com.reservation.entry.service;

import com.reservation.entry.json.ReservationJson;
import com.reservation.entry.domain.Reservation;
import com.reservation.message.ReservationMessageJson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.reservation.entry.mapper.ReservationMapper.toReservationMessageJson;
import static java.lang.String.format;

@Service
public class ReservationMessageService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String TOPIC = "artemas-topic";

    private final KafkaTemplate<String, ReservationMessageJson> kafkaTemplate;

    public ReservationMessageService(KafkaTemplate<String, ReservationMessageJson> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public Mono<ReservationJson> bookReservation(final Reservation reservation) {
        ReservationMessageJson reservationMessageJson = toReservationMessageJson(reservation);
        Message<ReservationMessageJson> message = MessageBuilder
                .withPayload(reservationMessageJson)
                .setHeader(KafkaHeaders.TOPIC, TOPIC)
                .build();

        LOGGER.info(format("Sending Message: %s", message));
        kafkaTemplate.send(message);
        return Mono.empty();
    }
}