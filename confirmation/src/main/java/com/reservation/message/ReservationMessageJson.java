package com.reservation.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReservationMessageJson {
    public String secret;
    public String name;
    public String surname;
    public Boolean hasPlusOne;
    public Integer plusOne;

    @Override
    public String toString() {
        return "ReservationMessageJson{" +
                "secret='" + secret + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", hasPlusOne=" + hasPlusOne +
                ", plusOne=" + plusOne +
                '}';
    }
}
