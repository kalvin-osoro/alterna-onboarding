package com.alternaonboarding.app.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "phoneNumber"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;


    private String fullName;


    private int nationalId;


    @Column(name = "date_of_birth", columnDefinition = "DATE DEFAULT '1970-01-01'")
    private Date dob;



//    @Column(name = "date_of_birth")
//    private Date dob;


    private String gender;


    private String phoneNumber;


    private String email;

    private String pin = String.valueOf(0000);

    private boolean verified = false;

    public User(String fullName, int nationalId, Date dob, String gender, String phoneNumber, String email, String pin ) {
        this.fullName = fullName;
        this.nationalId = nationalId;
        this.dob = dob;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.pin = pin;
    }


}
