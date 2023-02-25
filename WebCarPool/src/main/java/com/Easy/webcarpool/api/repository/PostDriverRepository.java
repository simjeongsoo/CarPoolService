package com.Easy.webcarpool.api.repository;

import com.Easy.webcarpool.api.domain.entity.PostDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostDriverRepository extends JpaRepository<PostDriver, Long> {

    @Query("SELECT p FROM PostDriver p WHERE p.email = :email")
    public List<PostDriver> findDriverPostByEmail(@Param("email") String email);

    @Query("SELECT p FROM PostDriver p WHERE p.departure LIKE %:district% OR p.destination LIKE %:district%")
    public List<PostDriver> findDriverPostByDistrict(@Param("district") String district);

    //시간순으로 findAll() 수행하도록 변경할것
}
