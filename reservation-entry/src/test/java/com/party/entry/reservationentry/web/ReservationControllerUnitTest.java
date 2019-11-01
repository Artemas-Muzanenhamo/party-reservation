package com.party.entry.reservationentry.web;

import com.party.entry.reservationentry.domain.ReservationJson;
import com.party.entry.reservationentry.dto.Reservation;
import com.party.entry.reservationentry.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReservationControllerUnitTest {
    private ReservationController reservationController;

    @Mock
    private ReservationService reservationServiceImpl;

    @BeforeEach
    void setUp() {
        reservationController = new ReservationController(reservationServiceImpl);
    }

    @Test
    @DisplayName("Should create a reservation when valid details are passed in")
    void createReservation() {
        ReservationJson reservationJson = new ReservationJson();
        Reservation reservation = new Reservation();
        reservationController.bookReservation(reservationJson);

        verify(reservationServiceImpl).bookReservation(reservation);
    }
}
