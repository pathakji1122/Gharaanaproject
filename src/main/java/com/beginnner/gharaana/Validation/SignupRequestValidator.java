package com.beginnner.gharaana.Validation;

import com.beginnner.gharaana.Service.CustomerSignupRequest;
import com.beginnner.gharaana.Service.WorkerSignupRequest;

public class SignupRequestValidator {

    public static String validateCustomerEmail(CustomerSignupRequest customerSignupRequest) {

        if (customerSignupRequest.email.contains("@gmail.com") == false) {
            return "Email invalid";
        }
        int c = customerSignupRequest.email.length();
        if (customerSignupRequest.email.substring(c - 10, c).equals("@gmail.com") == false) {
            return "Email Invalid";
        }
        return null;
    }

    public static String validateWorkerEmail(WorkerSignupRequest workerSignupRequest) {

        if (workerSignupRequest.email.contains("@gmail.com") == false) {
            return "Email invalid";
        }
        int c = workerSignupRequest.email.length();
        if (workerSignupRequest.email.substring(c - 10, c).equals("@gmail.com") == false) {
            return "Email Invalid";
        }
        return null;
    }

    public static String validateCustomerPhoneNo(CustomerSignupRequest customerSignupRequest) {
        if (customerSignupRequest.phoneNo.length() != 10) {
            return "Enter PhoneNo of 10 digits";
        }
        for (int i = 0; i < 10; i++) {
            if (Character.isDigit(customerSignupRequest.phoneNo.charAt(i)) == false) {
                return "PhoneNo consists only digits";
            }
        }
        return null;
    }

    public static String validateWorkerPhoneNo(WorkerSignupRequest workerSignupRequest) {
        if (workerSignupRequest.phoneNo.length() != 10) {
            return "Enter PhoneNo of 10 digits";
        }
        for (int i = 0; i < 10; i++) {
            if (Character.isDigit(workerSignupRequest.phoneNo.charAt(i)) == false) {
                return "PhoneNo consists only digits";
            }
        }
        return null;
    }

    public static String validateCustomerRequest(CustomerSignupRequest customerSignupRequest) {
        String emailValidate = validateCustomerEmail(customerSignupRequest);
        if (emailValidate != null) {
            return emailValidate;
        }
        String phoneValidate = validateCustomerPhoneNo(customerSignupRequest);
        if (phoneValidate != null) {
            return phoneValidate;

        }
        return null;
    }

    public static String validateWorkerRequest(WorkerSignupRequest workerSignupRequest) {
        String emailValidate = validateWorkerEmail(workerSignupRequest);
        if (emailValidate != null) {
            return emailValidate;
        }
        String phoneValidate = validateWorkerPhoneNo(workerSignupRequest);
        if (phoneValidate != null) {
            return phoneValidate;

        }
        return null;
    }


}
