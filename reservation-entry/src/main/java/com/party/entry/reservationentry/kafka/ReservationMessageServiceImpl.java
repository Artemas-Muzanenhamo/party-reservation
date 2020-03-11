package com.party.entry.reservationentry.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.party.entry.reservationentry.dto.Reservation;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

import static java.lang.String.format;

@Service
public class ReservationMessageServiceImpl implements ReservationMessageService {
    private static final Logger LOGGER = LogManager.getLogger();

    private Producer<String, String> producer;
    private ObjectMapper mapper;

    public ReservationMessageServiceImpl() { }

    public ReservationMessageServiceImpl(Producer<String, String> producer, ObjectMapper mapper) {
        this.producer = producer;
        this.mapper = mapper;
    }

    @Override
    public void bookReservation(final Reservation reservation) {
        try {
            String topic = "example-topic";
            String reservationMessageString = mapper.writeValueAsString(reservation);
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, reservationMessageString);

            Future<RecordMetadata> send = producer.send(producerRecord);
            if (send.isDone()) {
                LOGGER.info(format("Message sent: %s", producerRecord));
            }
        } catch (JsonProcessingException e) {
            LOGGER.error("There was a problem serialising the message. Exception: " + e);
        }
    }
}
