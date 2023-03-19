package com.beginnner.gharaana.Object;

public class GetOtpResponse {
    public long otp;
    public boolean status;
    public String response;

    public GetOtpResponse(Long otp, String responce, boolean status) {
        this.otp=otp;
        this.response =responce;
        this.status=status;
    }
}
