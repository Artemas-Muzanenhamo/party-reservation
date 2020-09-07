package com.reservation.confirmation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.confirmation.domain.Reservation;
import com.reservation.confirmation.exception.ReservationNotValidException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmationServiceImpl.class);
    private final ReactiveKafkaConsumerTemplate<Object, Object> kafkaConsumerTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ConfirmationServiceImpl(ReactiveKafkaConsumerTemplate<Object, Object> kafkaConsumerTemplate) {
        this.kafkaConsumerTemplate = kafkaConsumerTemplate;
    }

    @Override
    public Flux<Reservation> getReservations() {
        return kafkaConsumerTemplate.receive()
                .map(ConsumerRecord::value)
                .log(toString())
                .map(object -> {
                    try {
                        return objectMapper.readValue(object.toString(), Reservation.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return new Reservation();
                })
                .onErrorMap(e -> new ReservationNotValidException(e.getMessage()));
    }
}
