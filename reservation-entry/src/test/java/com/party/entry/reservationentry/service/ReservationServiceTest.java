package com.party.entry.reservationentry.service;

import com.party.entry.reservationentry.dto.Reservation;
import com.party.entry.reservationentry.exception.SecretAlreadyExistsException;
import com.party.entry.reservationentry.repository.ReservationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    private static final String EXCEPTION_MESSAGE = "Secret already exists! Please choose another secret";
    @InjectMocks
    private ReservationServiceImpl reservationServiceImpl;
    @Mock
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("Should add reservation")
    void addReservation() {
        Reservation reservation = new Reservation();
        when(reservationRepository.existsById(reservation.getSecret())).thenReturn(false);

        reservationServiceImpl.bookReservation(reservation);

        verify(reservationRepository).save(reservation);
    }

    @Test
    @DisplayName("Should throw a SecretAlreadyExistsException when trying to save a reservation with a secret that exists")
    void invalidReservationWithExistingSecret() {
        Reservation reservation = new Reservation();
        when(reservationRepository.existsById(reservation.getSecret())).thenReturn(true);

        SecretAlreadyExistsException exception = assertThrows(SecretAlreadyExistsException.class, () -> reservationServiceImpl.bookReservation(reservation));

        assertThat(exception.getMessage()).isEqualTo(EXCEPTION_MESSAGE);
        verify(reservationRepository, never()).save(reservation);
        verify(reservationRepository).existsById(reservation.getSecret());
    }
}