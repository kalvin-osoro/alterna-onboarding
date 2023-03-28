package com.alternaonboarding.app.controller;

import com.alternaonboarding.app.dto.ResponseDto;
import com.alternaonboarding.app.dto.user.LoginDto;
import com.alternaonboarding.app.dto.user.SignupDto;
import com.alternaonboarding.app.exceptions.CustomException;
import com.alternaonboarding.app.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("user")
@RestController
public class UserController {
    @Autowired
    UserServiceImpl userService;



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