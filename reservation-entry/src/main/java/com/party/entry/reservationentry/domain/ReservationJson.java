package com.party.entry.reservationentry.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReservationJson {
    private String secret;
    private String name;
    private String surname;
    private Boolean hasPlusOne;
    private Integer plusOne;

    public ReservationJson() { }

    @JsonCreator
    public ReservationJson(String secret, String name, String surname, Boolean hasPlusOne, Integer plusOne) {
        this.secret = secret;
        this.name = name;
        this.surname = surname;
        this.hasPlusOne = hasPlusOne;
        this.plusOne = plusOne;
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

    public String getSecret() {
        return secret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationJson that = (ReservationJson) o;
        return Objects.equals(secret, that.secret) &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(hasPlusOne, that.hasPlusOne) &&
                Objects.equals(plusOne, that.plusOne);
    }

    @Override
    public int hashCode() {
        return Objects.hash(secret, name, surname, hasPlusOne, plusOne);
    }
}
