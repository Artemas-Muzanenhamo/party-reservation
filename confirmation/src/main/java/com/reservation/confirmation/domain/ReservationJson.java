package com.reservation.confirmation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReservationJson {
    public String secret;
    public String name;
    public String surname;
    public Boolean hasPlusOne;
    public Integer plusOne;

    public ReservationJson(String secret, String name, String surname, Boolean hasPlusOne, Integer plusOne) {
        this.secret = secret;
        this.name = name;
        this.surname = surname;
        this.hasPlusOne = hasPlusOne;
        this.plusOne = plusOne;
    }
}
