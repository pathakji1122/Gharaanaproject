package com.beginnner.gharaana.Service;

public class Util {
    public static long generateOtp() {
        long min = 111111;
        long max = 999999;
        long otp = (long) (Math.random() * (max - min + 1) + min);
        return otp;
    }





}
