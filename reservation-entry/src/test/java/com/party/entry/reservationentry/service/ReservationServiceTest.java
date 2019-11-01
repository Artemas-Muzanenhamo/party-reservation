package com.party.entry.reservationentry.service;

import com.party.entry.reservationentry.dto.Reservation;
import com.party.entry.reservationentry.repository.ReservationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @InjectMocks
    private ReservationServiceImpl reservationServiceImpl;
    @Mock
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("Should add reservation")
    void addReservation() {
        Reservation reservation = new Reservation();

        reservationServiceImpl.bookReservation(reservation);

        verify(reservationRepository).save(reservation);
    }
}