package com.reservation.confirmation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReservationJson {
    private String secret;
    private String name;
    private String surname;
    private Boolean hasPlusOne;
    private Integer plusOne;

    public ReservationJson(String secret, String name, String surname, Boolean hasPlusOne, Integer plusOne) {
        this.secret = secret;
        this.name = name;
        this.surname = surname;
        this.hasPlusOne = hasPlusOne;
        this.plusOne = plusOne;
    }

    public ReservationJson() {
    }
}
