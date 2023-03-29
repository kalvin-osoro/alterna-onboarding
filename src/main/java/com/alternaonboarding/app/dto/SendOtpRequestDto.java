package com.alternaonboarding.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendOtpRequestDto {

    private String phoneNumber;//destination

//    private String userName;
    private String oneTimePassword;


}
