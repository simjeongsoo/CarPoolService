package com.Easy.webcarpool.api.repository;

import com.Easy.webcarpool.api.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //기존의 회원인지 새로 유입된 회원인지 식별하기 위해 사용

    @Transactional
    Optional<User> findUserByEmail(String email);


    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.driverAuthentication = true WHERE u.email = :email")
    void updateDriverAuthentication(String email);


    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.nickname = :nickname, u.gender = :gender WHERE u.email = :email")
    void updateProfile(String email, String nickname, String gender);


    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.rate = :rate WHERE u.email = :email")
    void updateRate(String email, Float rate);

}
