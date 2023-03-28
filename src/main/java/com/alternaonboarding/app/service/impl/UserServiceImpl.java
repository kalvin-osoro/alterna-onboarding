package com.alternaonboarding.app.service.impl;

import com.alternaonboarding.app.dto.ResponseDto;
import com.alternaonboarding.app.dto.user.LoginDto;
import com.alternaonboarding.app.dto.user.SetPinDto;
import com.alternaonboarding.app.dto.user.SignupDto;
import com.alternaonboarding.app.exceptions.CustomException;
import com.alternaonboarding.app.models.User;
import com.alternaonboarding.app.repository.UserRepository;
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

//    @Override
//    public ResponseDto setNewPin(String phoneNumber, String newPin, String confirmPin) throws CustomException {
//        User user = userRepository.findByPhoneNumber(phoneNumber);
//        if (user == null) {
//            throw new CustomException("User not found");
//        }
////        if (!user.isVerified()) {
////            throw new CustomException("User account not verified");
////        }
//        if (!newPin.equals(confirmPin)) {
//
//            throw new CustomException("new pin and confirm pin do not match");
//        }
//        if (newPin.length() != 4) {
//            throw new CustomException("new pin should be 4 characters long");
//        }
//        user.setPin(newPin);
//        userRepository.save(user);
//
//        return new ResponseDto("success", "pin changed successfully");
//    }

    @Override
    public ResponseDto setPin(SetPinDto setPinDto) throws CustomException {
        User user = userRepository.findByPhoneNumber(setPinDto.getPhoneNumber());
        if (user == null) {
            throw new CustomException("User not found");
        }
        if (!setPinDto.getNewPin().equals(setPinDto.getConfirmNewPin())) {
            throw new CustomException("New pin and confirm pin do not match");
        }
        if (setPinDto.getNewPin().length() != 4) {
            throw new CustomException("New pin should be 4 characters long");
        }
        user.setPin(setPinDto.getNewPin());
        userRepository.save(user);
        return new ResponseDto("success", "Pin changed successfully");
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

}

