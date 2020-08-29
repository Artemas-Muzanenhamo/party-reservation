package com.reservation.entry.web;

import com.reservation.entry.domain.ReservationJson;
import com.reservation.entry.dto.Reservation;
import com.reservation.entry.service.ReservationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.reservation.entry.mapper.ReservationMapper.toReservationDTO;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping(value = "/reservation", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public void bookReservation(@RequestBody ReservationJson reservationJson) {
        Reservation reservation = toReservationDTO(reservationJson);
        reservationService.bookReservation(reservation);
    }
}
