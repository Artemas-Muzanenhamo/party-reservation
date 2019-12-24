package com.party.entry.reservationentry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ReservationEntryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationEntryApplication.class, args);
    }

}
