package com.party.entry.reservationentry.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.party.entry.reservationentry.dto.Reservation;
import com.party.entry.reservationentry.exception.SecretAlreadyExistsException;
import com.party.entry.reservationentry.repository.ReservationRepository;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import static java.lang.String.format;

@Service
public class ReservationServiceImpl implements ReservationService {
    private static Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

    private final ReservationRepository reservationRepository;
    private final KafkaTemplate<String, String> template;
    private final ObjectMapper objectMapper;

    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  KafkaTemplate<String, String> template,
                                  ObjectMapper objectMapper) {
        this.reservationRepository = reservationRepository;
        this.template = template;
        this.objectMapper = objectMapper;
    }

    @Override
    public void bookReservation(Reservation reservation) {
        boolean secretExists = doesReservationAlreadyExist(reservation);
        if (!secretExists) {
            reservationRepository.save(reservation);
        } else {
            throw new SecretAlreadyExistsException("Secret already exists! Please choose another secret");
        }
    }

    @Override
    public void reserveBooking(Reservation reservation) throws JsonProcessingException {
        String reservationString = objectMapper.writeValueAsString(reservation);
        // new ProducerRecord<>(topicName, messageKey, jsonMessage);
        ProducerRecord<String, String> record = new ProducerRecord<>("example-topic", reservationString);
        ListenableFuture<SendResult<String, String>> message = template.send(record);
        if (message.isDone()) {
            logger.info(format("Message sent as: %s", message));
        }
    }

    boolean doesReservationAlreadyExist(Reservation reservation) {
        return reservationRepository.existsById(reservation.getSecret());
    }
}
