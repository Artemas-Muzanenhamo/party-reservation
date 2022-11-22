package com.reservation.entry.service;

import com.reservation.entry.domain.Reservation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.just;

@Service
public class ReservationMessageService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final KafkaTemplate<String, Reservation> kafkaTemplate;

    @Value("${topic.name.producer}")
    private String topicName;

    public ReservationMessageService(KafkaTemplate<String, Reservation> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public Mono<Reservation> bookReservation(final Reservation reservation) {
        LOGGER.info(reservation);
        kafkaTemplate.send(topicName, reservation);
        return just(reservation);
    }
}
