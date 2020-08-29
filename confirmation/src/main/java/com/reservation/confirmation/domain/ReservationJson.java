package com.reservation.confirmation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "ReservationJson{" +
                "secret='" + secret + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", hasPlusOne=" + hasPlusOne +
                ", plusOne=" + plusOne +
                '}';
    }
}
