package com.reservation.entry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class ReservationEntryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationEntryApplication.class, args);
    }

}
