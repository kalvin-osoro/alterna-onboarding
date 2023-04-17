package com.alternaonboarding.app.controller;

import com.alternaonboarding.app.dto.ResponseDto;
import com.alternaonboarding.app.dto.user.LoginDto;
import com.alternaonboarding.app.dto.user.SetPinDto;
import com.alternaonboarding.app.dto.user.SignupDto;
import com.alternaonboarding.app.exceptions.CustomException;
import com.alternaonboarding.app.service.impl.TwilioOTPServiceImpl;
import com.alternaonboarding.app.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("user")
@RestController
@RequiredArgsConstructor

@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:4200")

public class UserController {

   private final UserServiceImpl userService;


   private final TwilioOTPServiceImpl twilioOTPService;



    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupDto signupDto) throws CustomException {

        return userService.registerUser(signupDto);
    }



    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        ResponseDto responseDto = new ResponseDto();
        try {
            responseDto = userService.login(loginDto);
            responseDto.setMessage("Login successful");
        } catch (CustomException e) {
            responseDto.setStatus("error");
            responseDto.setMessage(e.getMessage());
        }
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("/old-login")
    public ResponseEntity<ResponseDto> oldLogin(@Valid @RequestBody LoginDto loginDto) {
        ResponseDto responseDto = new ResponseDto();
        try {
            responseDto = userService.oldLogin(loginDto);
            responseDto.setMessage("Login successful");
        } catch (CustomException e) {
            responseDto.setStatus("error");
            responseDto.setMessage(e.getMessage());
        }
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("/change-pin")
//    public ResponseEntity<ResponseDto> changePin(@RequestBody SetPinDto setPinDto, @RequestParam String newPin) {
    public ResponseEntity<ResponseDto> changePin(@RequestBody SetPinDto setPinDto) {
        ResponseDto responseDto = new ResponseDto();
        try {
//            responseDto = userService.setPin(setPinDto, newPin);
            responseDto = userService.setPin(setPinDto);
            responseDto.setMessage("PIN changed successfully");
        } catch (CustomException e) {
            responseDto.setStatus("error");
            responseDto.setMessage(e.getMessage());
        }
        return ResponseEntity.ok().body(responseDto);
    }

//    @PostMapping("/pin")
//    public ResponseEntity<ResponseDto> changePin(
//            @RequestParam(name = "phone_number") String phoneNumber,
//            @RequestParam(name = "new_pin") String newPin,
//            @RequestParam(name = "confirm_new_pin") String confirmNewPin) {
//        ResponseDto responseDto = new ResponseDto();
//
//        try {
//            responseDto = userService.setNewPin(phoneNumber, newPin, confirmNewPin);
//            responseDto.setMessage("PIN changed successfully");
//        } catch (CustomException e) {
//            responseDto.setStatus("error");
//            responseDto.setMessage(e.getMessage());
//        }
//        return ResponseEntity.ok().body(responseDto);
//    }


//    @PostMapping("/login")
//    public ResponseEntity<ResponseDto> login (@RequestBody LoginDto loginDto) throws CustomException {
//
//        ResponseDto responseDto = userService.login(loginDto);
//        responseDto.setMessage("Login successful");
//        return ResponseEntity.ok().body(responseDto);
////        String phonenumber = loginDto.getPhoneNumber();
////        String pin = loginDto.getPin();
////        if (userService.login(loginDto)) {
////            return ResponseEntity.ok("Login Successful!");
////        }
////        return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("Invalid phone number or pin");
//    }

}