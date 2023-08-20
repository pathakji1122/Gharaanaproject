package com.beginnner.gharaana.Validation;

import com.beginnner.gharaana.Object.CustomerSignUpRequest;
import com.beginnner.gharaana.Object.ExpertSignupRequest;

public class SignupRequestValidator {

    public static String validateCustomerEmail(CustomerSignUpRequest customerSignupRequest) {

        if (customerSignupRequest.email.contains("@gmail.com") == false) {
            return "Email invalid";
        }
        int c = customerSignupRequest.email.length();
        if (customerSignupRequest.email.substring(c - 10, c).equals("@gmail.com") == false) {
            return "Email Invalid";
        }
        return null;
    }

    public static String validateExpertEmail(ExpertSignupRequest expertSignupRequest) {

        if (expertSignupRequest.email.contains("@gmail.com") == false) {
            return "Email invalid";
        }
        int c = expertSignupRequest.email.length();
        if (expertSignupRequest.email.substring(c - 10, c).equals("@gmail.com") == false) {
            return "Email Invalid";
        }
        return null;
    }

    public static String validateCustomerPhoneNo(CustomerSignUpRequest customerSignupRequest) {
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

    public static String validateExpertPhoneNo(ExpertSignupRequest expertSignupRequest) {
        if (expertSignupRequest.phoneNo.length() != 10) {
            return "Enter PhoneNo of 10 digits";
        }
        for (int i = 0; i < 10; i++) {
            if (Character.isDigit(expertSignupRequest.phoneNo.charAt(i)) == false) {
                return "PhoneNo consists only digits";
            }
        }
        return null;
    }

    public static String validateCustomerRequest(CustomerSignUpRequest customerSignupRequest) {
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

    public static String validateExpertRequest(ExpertSignupRequest expertSignupRequest) {
        String emailValidate = validateExpertEmail(expertSignupRequest);
        if (emailValidate != null) {
            return emailValidate;
        }
        String phoneValidate = validateExpertPhoneNo(expertSignupRequest);
        if (phoneValidate != null) {
            return phoneValidate;

        }
        return null;
    }


}
