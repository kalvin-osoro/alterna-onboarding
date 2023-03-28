package com.alternaonboarding.app.dto.user;

import lombok.Data;

@Data
public class SetPinDto {

    private String phoneNumber;
    private String newPin;
    private String confirmNewPin;

}
