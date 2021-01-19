package com.reservation.entry.web;

import com.reservation.entry.domain.ReservationJson;
import com.reservation.entry.service.ReservationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReservationEndpointUnitTest {
    private static final String SECRET = "some secret party word";
    private static final String NAME = "artemas";
    private static final String SURNAME = "muzanenhamo";
    private static final boolean HAS_PLUS_ONE = false;
    private static final int PLUS_ONE = 0;

    @InjectMocks
    private ReservationEndpoint reservationEndpoint;
    @Mock
    private ReservationService reservationServiceImpl;

    @Test
    @DisplayName("Should create a reservation when valid details are passed in")
    void createReservation() {
        Mono<ReservationJson> reservationJsonMono = Mono.just(new ReservationJson(SECRET, NAME, SURNAME, HAS_PLUS_ONE, PLUS_ONE));

        reservationEndpoint.bookReservation(reservationJsonMono);

        verify(reservationServiceImpl).bookReservation(any(Mono.class));
    }
}
