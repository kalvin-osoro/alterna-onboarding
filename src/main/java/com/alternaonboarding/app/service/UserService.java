package com.alternaonboarding.app.service;

import com.alternaonboarding.app.dto.ResponseDto;
import com.alternaonboarding.app.dto.SendOtpRequestDto;
import com.alternaonboarding.app.dto.SendOtpResponseDto;
import com.alternaonboarding.app.dto.user.LoginDto;
import com.alternaonboarding.app.dto.user.SignupDto;
import com.alternaonboarding.app.exceptions.CustomException;

public interface UserService {

    ResponseDto registerUser(SignupDto signupDto) throws CustomException;

    ResponseDto setNewPin(String phoneNumber,String newPin) throws CustomException;
    ResponseDto login(LoginDto loginDto) throws CustomException;
}
