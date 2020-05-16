package com.reservation.entry.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.entry.domain.ReservationMessageJson;
import com.reservation.entry.dto.Reservation;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.reservation.entry.mapper.ReservationMapper.toReservationMessageJson;

@Service
public class ReservationMessageServiceImpl implements ReservationMessageService {
    private static final Logger LOGGER = LogManager.getLogger();

    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper mapper;

    public ReservationMessageServiceImpl(KafkaTemplate<String, String> kafkaTemplate,
                                         ObjectMapper mapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
    }

    @Override
    public void bookReservation(final Reservation reservation) {
        try {
            ReservationMessageJson reservationMessageJson = toReservationMessageJson(reservation);
            String reservationMessageString = mapper.writeValueAsString(reservationMessageJson);
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("artemas-topic", reservationMessageString);

            kafkaTemplate.send(producerRecord);
        } catch (JsonProcessingException e) {
            LOGGER.error("There was a problem serialising the message. Exception: " + e);
        }
    }
}
