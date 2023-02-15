package com.beginnner.gharaana.Entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Expertise {
    DELIVERY,
    DRIVER,
    MECHANIC,
    ASSISTANT;
    @JsonCreator
    public static Expertise checkExpertise(String value) {

        for (Expertise expertise : Expertise.values()) {

            if (expertise.toString().equals(value)) {
                return expertise;
            }
        }
        return null;
    }
}
