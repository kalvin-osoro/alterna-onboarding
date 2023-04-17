package com.alternaonboarding.app.service.impl;

import com.alternaonboarding.app.dto.ResponseDto;
import com.alternaonboarding.app.dto.SendOtpRequestDto;
import com.alternaonboarding.app.dto.user.LoginDto;
import com.alternaonboarding.app.dto.user.SetPinDto;
import com.alternaonboarding.app.dto.user.SignupDto;
import com.alternaonboarding.app.exceptions.CustomException;
import com.alternaonboarding.app.models.User;
import com.alternaonboarding.app.repository.UserRepository;
import com.alternaonboarding.app.service.UserService;
import com.alternaonboarding.app.utils.CommonFunctions;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {



    private final UserRepository userRepository;

   private final TwilioOTPServiceImpl twilioOTPService;

   private final JavaMailSender mailSender;

    private static CommonFunctions commonFunctions;


   @Transactional
   @Override
   public ResponseDto registerUser(SignupDto signupDto) throws CustomException {
       if (userRepository.findByEmail(signupDto.getEmail()) !=null) {
           throw  new CustomException("Email already exists");
       }

       if (Objects.nonNull(userRepository.findByPhoneNumber(signupDto.getPhoneNumber()))) {
           throw  new CustomException("Phone number already exists");
       }
       if (signupDto.getPhoneNumber() == null && signupDto.getConfirmPhoneNumber() == null) {
           throw new IllegalArgumentException("Phone number is required");
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

        User savedUser = userRepository.save(user);
        //send verification code
       String verificationCode = commonFunctions.generateOTP();
       twilioOTPService.sendOtpForVerification(
               SendOtpRequestDto.builder()
                       .phoneNumber(signupDto.getPhoneNumber())
                       .build()
       );

//       //send confirmation email
//       SimpleMailMessage mailMessage = new SimpleMailMessage();
//
//
////           MimeMessage message = mailSender.createMimeMessage();
////           MimeMessageHelper helper = new MimeMessageHelper(message, true);
//       mailMessage.setTo(signupDto.getEmail());
//       mailMessage.setSubject("Confirm your email");
//       mailMessage.setText("<html><body><p>Thank you for registering with us.</p>"
//                                          + "<p>Please click on the following link to confirm your email and activate your account:</p>"
//                                           +   "<p><a href='http://example.com/confirm-email?code=" + verificationCode + "'>Confirm email</a></p>"
//                                            +  "</body></html>");
//       mailSender.send(mailMessage);
//
       ResponseDto responseDto = new ResponseDto("success", "User added successfully");
           return responseDto;

       }

       //.......................................................................................................................
       @Transactional
       @Override
       public ResponseDto registerUserWeb(SignupDto signupDto) throws CustomException {
           if (Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))) {
               throw  new CustomException("User with email already exists");
           }
           if (!signupDto.getPhoneNumber().equals(signupDto.getConfirmPhoneNumber())) {
               throw new CustomException("Phone numbers do not match");
           }
           User user = new User();
           user.setFullName(signupDto.getFullName());
           user.setNationalId(signupDto.getNationalId());
//           user.setDob(signupDto.getDob());
           user.setGender(signupDto.getGender());
           user.setPhoneNumber(signupDto.getPhoneNumber());
           user.setEmail(signupDto.getEmail());
           user.setPin(signupDto.getPin());

           User savedUser = userRepository.save(user);

           //send confirmation email
           SimpleMailMessage mailMessage = new SimpleMailMessage();


//           MimeMessage message = mailSender.createMimeMessage();
//           MimeMessageHelper helper = new MimeMessageHelper(message, true);
           mailMessage.setTo(signupDto.getEmail());
           mailMessage.setSubject("Confirm your email");
           mailMessage.setText("<html><body><p>Thank you for registering with us.</p>"
                   + "<p>Please click on the following link to confirm your email and activate your account:</p>"
                   +   "<p><a href='http://example.com/confirm-email?'>Confirm email</a></p>"
                   +  "</body></html>");
           mailSender.send(mailMessage);

           ResponseDto responseDto = new ResponseDto("success", "User added successfully");
           return responseDto;

       }

       //.......................................................................................................................



    @Override
    public ResponseDto setPin(SetPinDto setPinDto) throws CustomException {
        User user = userRepository.findByPhoneNumber(setPinDto.getPhoneNumber());
        if (user == null) {
            throw new CustomException("User not found");
        }
//        if (!user.isVerified()) {
//            throw new CustomException("User account not verified");
//        }
        if (!setPinDto.getNewPin().equals(setPinDto.getConfirmNewPin())) {
            throw new CustomException("New pin and confirm pin do not match");
        }
        if (setPinDto.getNewPin().length() != 6) {
            throw new CustomException("New pin should be 6 characters long");
        }
        user.setPin(setPinDto.getNewPin());
        user.setVerified(true);
        userRepository.save(user);
        return new ResponseDto("success", "Pin changed successfully");
    }

    @Transactional
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
        //send verification code
        String verificationCode = commonFunctions.generateOTP();
        twilioOTPService.sendOtpForVerification(
                SendOtpRequestDto.builder()
                        .phoneNumber(loginDto.getPhoneNumber())

                        .build()
        );
        return new ResponseDto("success", "Login successful");

    }

    @Override
    public ResponseDto oldLogin(LoginDto loginDto) throws CustomException {
        User user = userRepository.findByPin(loginDto.getPin());
//        Optional<User> user = userRepository.findById(loginDto.)

        if (user ==null) {
            throw new CustomException("User not found");
        }
        if (!user.isVerified()) {
            throw new CustomException("User account not verified");
        }
        if (!user.getPin().equals(loginDto.getPin())) {
            throw new CustomException("Incorrect Pin");
        }
        //send verification code
        String verificationCode = commonFunctions.generateOTP();
        twilioOTPService.sendOtpForVerification(
                SendOtpRequestDto.builder()
                        .phoneNumber(loginDto.getPhoneNumber())
                        .build()
        );
        return new ResponseDto("success", "Login successful");
    }



}

