package com.party.entry.reservationentry.repository;

import com.party.entry.reservationentry.dto.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReservationRepository extends MongoRepository<Reservation, String> {
}
