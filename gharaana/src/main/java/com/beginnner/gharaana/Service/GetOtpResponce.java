package com.beginnner.gharaana.Service;

public class GetOtpResponce {
    public long otp;
    public boolean status;
    public String responce;

    public GetOtpResponce(Long otp, String responce, boolean status) {
        this.otp=otp;
        this.responce=responce;
        this.status=status;
    }
}
