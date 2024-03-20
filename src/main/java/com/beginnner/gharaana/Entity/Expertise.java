package com.beginnner.gharaana.Entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Expertise {
    PACKERS_MOVERS,
    BIKE_MECHANIC,
    CAR_MECHANIC,
    VEHICLE_WASHING,
    CAR_SHARING ,
    DRIVER,
    VEHICLE_MODIFICATION,
    VEHICLE_RENTAL,
    LAWN_MAINTENANCE,
    HOUSE_CLEANING,
    AC_REPAIRING,
    TV_REPAIRING,
    WASHING_MACHINE,
    FRIDGE,
    ENGINEER,
    DOCTOR,
    HOUSECLEANING,
    PAINTING,
    CARPENTER,
    PLUMBER,
    YOGAINSTRUCTOR;


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
