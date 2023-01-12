package com.Easy.webcarpool.repository;

import com.Easy.webcarpool.model.UserCar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCarRepository extends JpaRepository<UserCar, Long> {

}
