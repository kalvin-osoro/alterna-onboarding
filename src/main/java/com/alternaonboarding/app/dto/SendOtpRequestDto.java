package com.alternaonboarding.app.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendOtpRequestDto {

    private String phoneNumber;//destination

//    private String userName;
    private String oneTimePassword;


}
