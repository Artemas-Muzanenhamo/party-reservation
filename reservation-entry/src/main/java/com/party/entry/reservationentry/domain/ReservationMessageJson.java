package com.party.entry.reservationentry.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReservationMessageJson {
    private String secret;
    private String name;
    private String surname;
    private Boolean hasPlusOne;
    private Integer plusOne;

    public ReservationMessageJson() { }

    public ReservationMessageJson(String secret, String name, String surname, Boolean hasPlusOne, Integer plusOne) {
        this.secret = secret;
        this.name = name;
        this.surname = surname;
        this.hasPlusOne = hasPlusOne;
        this.plusOne = plusOne;
    }

    public String getSecret() {
        return secret;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Boolean getHasPlusOne() {
        return hasPlusOne;
    }

    public Integer getPlusOne() {
        return plusOne;
    }
}
