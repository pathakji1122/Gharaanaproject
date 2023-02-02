package com.beginnner.gharaana.Entity;


import com.fasterxml.jackson.annotation.JsonCreator;

public enum Location {
    DELHI, JAIPUR, GUNTAKAL, MUMBAI, HYDERABAD;

   @JsonCreator
    public static Location getLocationFromCode(String value) {

       for (Location locate : Location.values()) {

           if (locate.toString().equals(value)) {
               return locate;
           }
       }
       return null;
   }


}
