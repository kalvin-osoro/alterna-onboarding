package com.alternaonboarding.app.service.impl;

import com.alternaonboarding.app.dto.ResponseDto;
import com.alternaonboarding.app.dto.SendOtpRequestDto;
import com.alternaonboarding.app.dto.SendOtpResponseDto;
import com.alternaonboarding.app.dto.user.LoginDto;
import com.alternaonboarding.app.dto.user.SignupDto;
import com.alternaonboarding.app.exceptions.CustomException;
import com.alternaonboarding.app.models.User;
import com.alternaonboarding.app.repository.UserRepository;
import com.alternaonboarding.app.service.TwilioOTPService;
import com.alternaonboarding.app.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;


   @Transactional
   @Override
   public ResponseDto registerUser(SignupDto signupDto) throws CustomException {
       if (Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))) {
           throw  new CustomException("User with email already exists");
       }
       if (!signupDto.getPhoneNumber().equals(signupDto.getConfirmPhoneNumber())) {
           throw new CustomException("Phone numbers do not match");
       }
           User user = new User();
           user.setFullName(signupDto.getFullName());
           user.setNationalId(signupDto.getNationalId());
           user.setDob(signupDto.getDob());
           user.setGender(signupDto.getGender());
           user.setPhoneNumber(signupDto.getPhoneNumber());
           user.setEmail(signupDto.getEmail());
           user.setPin(signupDto.getPin());

           userRepository.save(user);

           ResponseDto responseDto = new ResponseDto("success", "User added successfully");
           return responseDto;

       }

    @Override
    public ResponseDto setNewPin(String phoneNumber, String newPin) throws CustomException {
        return null;
    }


    @Override
    public ResponseDto login(LoginDto loginDto) throws CustomException {
//        Optional<User> user = userRepository.findByPhoneNumber(loginDto.getPhoneNumber());
        User user = userRepository.findByPhoneNumber(loginDto.getPhoneNumber());
        if (user ==null) {
            throw new CustomException("User not found");
        }
        if (!user.isVerified()) {
            throw new CustomException("User account not verified");
        }
        if (!user.getPin().equals(loginDto.getPin())) {
            throw new CustomException("Incorrect Pin");
        }
        return new ResponseDto("success", "Login successful");
    }


//    public Mono<SendOtpResponseDto> registerUser(SendOtpRequestDto sendOtpRequestDto) {
//        return twilioOTPService.sendOtpForVerification(sendOtpRequestDto);
//    }

//    public Mono<String> registerUser(SendOtpRequestDto sendOtpRequestDto) {
////        return twilioOTPService.sendOtpForVerification(sendOtpRequestDto)
////                .flatMap(response -> {
//////                    if (response.getStatus().equalsIgnoreCase("pending")) {
////                    if (response.getStatus() != null && response.getStatus().equals("pending")) {
////                        return Mono.just(response);
////                    } else {
//                        User user = User.builder()
//                                .fullname(sendOtpRequestDto.getFullName())
//                                .nationalId(sendOtpRequestDto.getNationalId())
//                                .dob(sendOtpRequestDto.getDob())
//                                .gender(sendOtpRequestDto.getGender())
//                                .phoneNumber(sendOtpRequestDto.getPhoneNumber())
//                                .email(sendOtpRequestDto.getEmail())
//                                .verified(true) // Set the verified flag to true
//                                .build();
//                        userRepository.save(user);
//                        return Mono.just("user registered susssfully");
////                    }
////                });
//    }



//    public Mono<String> validateOtp(String userInputOtp, String username) {
//        return twilioOTPService.validateOTP(userInputOtp, username);
//    }
//public Mono<String> validateOtp(String userInputOtp, String username) {
//    return twilioOTPService.validateOTP(userInputOtp, username);
//}





}

