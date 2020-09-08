package com.reservation.confirmation.service;

import com.reservation.confirmation.ReservationConverter;
import com.reservation.confirmation.domain.Reservation;
import com.reservation.confirmation.exception.ReservationNotValidException;
import com.reservation.message.ReservationMessageJson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmationServiceImpl.class);
    private final ReactiveKafkaConsumerTemplate<String, ReservationMessageJson> kafkaConsumerTemplate;

    public ConfirmationServiceImpl(ReactiveKafkaConsumerTemplate<String, ReservationMessageJson> kafkaConsumerTemplate) {
        this.kafkaConsumerTemplate = kafkaConsumerTemplate;
    }

    @Override
    public Flux<Reservation> getReservations() {
        return kafkaConsumerTemplate.receive()
                .map(ConsumerRecord::value)
                .log(toString())
                .map(ReservationConverter::toReservation)
                .onErrorMap(exception -> new ReservationNotValidException(exception.getMessage()));
    }
}
