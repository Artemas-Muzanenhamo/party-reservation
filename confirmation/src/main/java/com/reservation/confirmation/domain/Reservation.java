package com.reservation.confirmation.domain;

public class Reservation {
    private String secret;
    private String name;
    private String surname;
    private Boolean hasPlusOne;
    private Integer plusOne;

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
}
