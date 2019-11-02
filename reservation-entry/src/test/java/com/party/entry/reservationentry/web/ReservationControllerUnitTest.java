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
    private static final String SECRET = "some secret party word";
    private static final String NAME = "artemas";
    private static final String SURNAME = "muzanenhamo";
    private static final boolean HAS_PLUS_ONE = false;
    private static final int PLUS_ONE = 0;
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
        ReservationJson reservationJson = new ReservationJson(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);
        Reservation reservation = new Reservation(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE);
        reservationController.bookReservation(reservationJson);

        verify(reservationServiceImpl).bookReservation(reservation);
    }
}
