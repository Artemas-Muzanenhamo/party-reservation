package com.reservation.confirmation.service;

import com.reservation.confirmation.domain.Reservation;
import com.reservation.confirmation.kafka.KafkaConsumer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.ReceiverOffset;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {
    private KafkaConsumer kafkaConsumer;

    public ConfirmationServiceImpl(KafkaConsumer kafkaConsumer) {
        this.kafkaConsumer = kafkaConsumer;
    }

    @Override
    public Flux<Reservation> getReservations() {
        kafkaConsumer.consumeMessages()
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
