package com.alternaonboarding.app.dto.user;

import lombok.Data;

@Data
public class LoginDto {
    private String phoneNumber;
    private String pin;
}
