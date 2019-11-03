package com.party.entry.reservationentry.service;

import com.party.entry.reservationentry.dto.Reservation;
import com.party.entry.reservationentry.exception.SecretAlreadyExistsException;
import com.party.entry.reservationentry.repository.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void bookReservation(Reservation reservation) {
        boolean secretExists = reservationRepository.existsById(reservation.getSecret());
        if (!secretExists) {
            reservationRepository.save(reservation);
        } else {
            throw new SecretAlreadyExistsException("Secret already exists! Please choose another secret");
        }
    }
}
