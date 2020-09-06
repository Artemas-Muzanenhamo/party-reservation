package com.reservation.confirmation.service;

import com.reservation.confirmation.domain.Reservation;
import com.reservation.confirmation.exception.ReservationNotValidException;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.ReceiverOffset;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {
    private final ReactiveKafkaConsumerTemplate<Object, Object> kafkaConsumerTemplate;

    public ConfirmationServiceImpl(ReactiveKafkaConsumerTemplate<Object, Object> kafkaConsumerTemplate) {
        this.kafkaConsumerTemplate = kafkaConsumerTemplate;
    }

    @Override
    public Flux<Reservation> getReservations() {
        kafkaConsumerTemplate.receive()
                .onErrorMap(e -> new ReservationNotValidException(e.getMessage()))
                .subscribe(record -> {
                    ReceiverOffset offset = record.receiverOffset();
                    System.out.printf("Received message: topic-partition=%s offset=%d key=%s value=%s\n",
                            offset.topicPartition(),
                            offset.offset(),
                            record.key(),
                            record.value());
                    offset.acknowledge();
                });

        return Flux.empty();
    }
}
