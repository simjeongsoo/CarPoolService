package com.Easy.SpringSecurity.repository;

import com.Easy.SpringSecurity.model.Board;
import com.Easy.SpringSecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
