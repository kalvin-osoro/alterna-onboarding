package com.alternaonboarding.app.service.impl;


import com.alternaonboarding.app.config.TwilioConfig;
import com.alternaonboarding.app.dto.OtpStatus;
import com.alternaonboarding.app.dto.SendOtpRequestDto;
import com.alternaonboarding.app.dto.SendOtpResponseDto;
import com.alternaonboarding.app.models.User;
import com.alternaonboarding.app.repository.UserRepository;
import com.alternaonboarding.app.service.Otp;
import com.alternaonboarding.app.service.TwilioOtpService;
import com.alternaonboarding.app.utils.CommonFunctions;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;


@Service
public class TwilioOTPServiceImpl implements TwilioOtpService {
    @Autowired
    private TwilioConfig twilioConfig;

    @Autowired
    UserRepository userRepository;

    private Map<String, Otp> otpMap = new HashMap<>();

    public Mono<SendOtpResponseDto> sendOtpForVerification(SendOtpRequestDto sendOtpRequestDto) {

        SendOtpResponseDto sendOtpResponseDto = null;
        try {
            PhoneNumber to = new PhoneNumber( sendOtpRequestDto.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
            String otp = CommonFunctions.generateOTP();
            String otpMessage = "An activation code "+ otp + " has been sent to your phone number. It will expire in 2 minutes.";
            Message message = Message
                    .creator(to, from,
                            otpMessage)
                    .create();
            otpMap.put(sendOtpRequestDto.getPhoneNumber(), new Otp(otp, System.currentTimeMillis()));

            sendOtpResponseDto = new SendOtpResponseDto(OtpStatus.DELIVERED, otpMessage);
        } catch (Exception ex) {
            sendOtpResponseDto = new SendOtpResponseDto(OtpStatus.FAILED, ex.getMessage());

        }
        return Mono.just(sendOtpResponseDto);
    }





    public Mono<String> validateOTP(String userInputOtp, String phoneNumber) {
        Otp otp = otpMap.get(phoneNumber);
        if (otp != null) {
            String generatedOtp = otp.getOtp();
            long generationTime = otp.getGenerationTime();
            long currentTime = System.currentTimeMillis();
            if (generatedOtp.equals(userInputOtp) && currentTime - generationTime <= 120000) {
                otpMap.remove(phoneNumber);

                User user = userRepository.findByPhoneNumber(phoneNumber);
                user.setVerified(true); //set verified to true
                userRepository.save(user);
                return Mono.just("Valid OTP, please set new pin");

            } else if (generatedOtp.equals(userInputOtp) && currentTime - generationTime > 120000) {
                otpMap.remove(phoneNumber);
                return Mono.error(new IllegalArgumentException("OTP expired, please request for a new one"));
            }
        }
        return Mono.error(new IllegalArgumentException("Invalid OTP, please retry"));
    }



}

