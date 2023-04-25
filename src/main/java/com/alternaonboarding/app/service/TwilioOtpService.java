package com.alternaonboarding.app.service;

import com.alternaonboarding.app.dto.SendOtpRequestDto;
import com.alternaonboarding.app.dto.SendOtpResponseDto;
import reactor.core.publisher.Mono;

public interface TwilioOtpService {

    Mono<SendOtpResponseDto> sendOtpForVerification(SendOtpRequestDto sendOtpRequestDto);

    Mono<String> validateOTP(String userInputOtp, String phoneNumber);
}