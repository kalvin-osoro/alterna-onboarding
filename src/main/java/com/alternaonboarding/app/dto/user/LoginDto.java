package com.alternaonboarding.app.dto.user;

import com.alternaonboarding.app.models.User;
import lombok.Data;

@Data
public class LoginDto {

    private User user;

    private String phoneNumber;
    private String pin;
//    private String oneTimePassword;
}
