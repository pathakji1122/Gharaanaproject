package com.beginnner.gharaana.Validation;

import com.beginnner.gharaana.Object.OrderRequest;

public class OrderRequestValidator {
    public static String validate(OrderRequest orderRequest) {
        String date = orderRequest.placedFor;
        String error = validateDate(date);
        if (error != null) {
            return error;
        }

        return null;

    }

    public static String validateDate(String date) {
        if (date == null || date.length() == 0) {
            return "Date cant be null or empty";
        }
        if (date.length() != 13) {
            return "Enter the Date as hh:dd:mm:yyyy";
        }

        String[] dateComponent = date.split(":", 5);
        int hour = Integer.parseInt(dateComponent[0]);
        int day = Integer.parseInt(dateComponent[1]);
        int month = Integer.parseInt(dateComponent[2]);
        if (hour > 24) {
            return "Enter hour in 24-hr format";
        }
        if (month > 12 || month == 0) {
            return "Enter a valid month";

        }


        return null;
    }


}
