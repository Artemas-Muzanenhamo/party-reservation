package com.party.entry.reservationentry.web;

import com.party.entry.reservationentry.domain.ReservationJson;
import com.party.entry.reservationentry.dto.Reservation;
import com.party.entry.reservationentry.service.ReservationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.party.entry.reservationentry.mapper.ReservationMapper.toReservationDTO;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping(value = "/reservation", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public void bookReservation(@RequestBody ReservationJson reservationJson) {
        Reservation reservation = toReservationDTO(reservationJson);
        reservationService.bookReservation(reservation);
    }

    @PostMapping(value = "/reservation-kafka", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public void bookReservationKafks(@RequestBody ReservationJson reservationJson) {
        Reservation reservation = toReservationDTO(reservationJson);
        reservationService.reserveBooking(reservation);
    }
}
