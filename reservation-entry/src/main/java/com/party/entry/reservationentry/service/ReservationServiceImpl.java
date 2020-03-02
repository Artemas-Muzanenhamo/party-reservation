package com.party.entry.reservationentry.service;

import com.party.entry.reservationentry.dto.Reservation;
import com.party.entry.reservationentry.exception.SecretAlreadyExistsException;
import com.party.entry.reservationentry.repository.ReservationRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final KafkaTemplate<String, Reservation> template;

    public ReservationServiceImpl(ReservationRepository reservationRepository, KafkaTemplate<String, Reservation> template) {
        this.reservationRepository = reservationRepository;
        this.template = template;
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
    public void reserveBooking(Reservation reservation) {
        ListenableFuture<SendResult<String, Reservation>> message = template.send("example-topic", reservation);
        if (message.isDone()) {
            // Log that message is done
        }
    }

    boolean doesReservationAlreadyExist(Reservation reservation) {
        return reservationRepository.existsById(reservation.getSecret());
    }
}
