package com.alternaonboarding.app.dto.user;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {


    private String fullName;
//    private int nationalId;
    private int idNumber;

    @Column(name = "date_of_birth")
//    private Date dob;
    private Date dateOfBirth;

    private String gender;

    private String phoneNumber;//destination

    private String confirmPhoneNumber;

    private String email;

    private String pin = String.valueOf(000000);

}
