package com.reservation.entry.service;

import com.reservation.entry.domain.Reservation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.just;

@Service
public class ReservationMessageService {
    @Value("${topic.name.producer}")
    private static String topicName;

    private static final Logger LOGGER = LogManager.getLogger();

    private final KafkaTemplate<String, Reservation> kafkaTemplate;

    public ReservationMessageService(KafkaTemplate<String, Reservation> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public Mono<Reservation> bookReservation(final Reservation reservation) {
        Message<Reservation> message = MessageBuilder
                .withPayload(reservation)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .build();

        LOGGER.info(message);
        kafkaTemplate.send(message);
        return just(reservation);
    }
}
