package com.Easy.SpringSecurity.service;

import com.Easy.SpringSecurity.model.Role;
import com.Easy.SpringSecurity.model.User;
import com.Easy.SpringSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//user 관련 비즈니스 로직 서비스 클래스
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user){
        //password 암호화
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnable(true);
        Role role = new Role();
        role.setId(2l);// 권한부여
        user.getRoles().add(role);
        return userRepository.save(user);
    }
}
