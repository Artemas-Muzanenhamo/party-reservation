package com.reservation.confirmation.domain;

import java.util.Objects;

public class Reservation {
    private String secret;
    private String name;
    private String surname;
    private Boolean hasPlusOne;
    private Integer plusOne;

    public Reservation() { }

    public Reservation(String secret, String name, String surname, Boolean hasPlusOne, Integer plusOne) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
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
        return "Reservation{" +
                "secret='" + secret + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", hasPlusOne=" + hasPlusOne +
                ", plusOne=" + plusOne +
                '}';
    }
}
