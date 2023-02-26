package com.Easy.webcarpool.web.repository;

import com.Easy.webcarpool.web.model.UserCar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCarRepository extends JpaRepository<UserCar, Long> {

    UserCar findByUser_Id(Long userId);
}
