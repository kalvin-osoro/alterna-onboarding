package com.alternaonboarding.app.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendOtpResponseDto {

    private OtpStatus status;
    private String message;


//    public SendOtpResponseDto(String success, String message) {
//
//    }

//    public SendOtpResponseDto(OtpStatus delivered, String otpMessage) {
//
//    }

//    public SendOtpResponseDto(OtpStatus delivered, String otpMessage) {
//
//    }
}
