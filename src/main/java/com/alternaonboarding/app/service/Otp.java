package com.alternaonboarding.app.service;

public class Otp {
    private String otp;
    private long generationTime;

    public Otp(String otp, long generationTime) {
        this.otp = otp;
        this.generationTime = generationTime;
    }

    public String getOtp() {
        return otp;
    }

    public long getGenerationTime() {
        return generationTime;
    }
}
