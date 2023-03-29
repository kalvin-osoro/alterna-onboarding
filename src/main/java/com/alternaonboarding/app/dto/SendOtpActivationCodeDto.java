package com.alternaonboarding.app.dto;

import lombok.Data;

@Data
public class SendOtpActivationCodeDto {

    private String phoneNumber;
    private String pin;
    private String oneTimePassword;
}
