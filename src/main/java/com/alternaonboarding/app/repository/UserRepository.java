package com.alternaonboarding.app.repository;

import com.alternaonboarding.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

//    Optional<User> findByPhoneNumber(String phoneNumber);
    User findByPhoneNumber(String phoneNumber);

    User findByPin(String pin);

    Optional<User> findById(Long id);

}
