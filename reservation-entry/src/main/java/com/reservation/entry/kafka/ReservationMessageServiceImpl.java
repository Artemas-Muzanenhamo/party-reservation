package com.reservation.entry.kafka;

import com.reservation.entry.domain.ReservationJson;
import com.reservation.entry.dto.Reservation;
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
public class ReservationMessageServiceImpl implements ReservationMessageService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String TOPIC = "artemas-topic";

    private final KafkaTemplate<String, ReservationMessageJson> kafkaTemplate;

    public ReservationMessageServiceImpl(KafkaTemplate<String, ReservationMessageJson> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
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
