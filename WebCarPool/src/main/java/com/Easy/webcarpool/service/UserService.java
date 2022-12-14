package com.Easy.webcarpool.service;

import com.Easy.webcarpool.dto.SignUpRequestForm;
import com.Easy.webcarpool.model.Role;
import com.Easy.webcarpool.model.User;
import com.Easy.webcarpool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//user 관련 비즈니스 로직 서비스 클래스
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    //--회원 가입--//
    @Transactional
    public User join(SignUpRequestForm form){

        User user = new User();
        user.setRealname(form.getRealname()); // 실명 설정
        user.setUsername(form.getUsername()); // 아이디 설정
        user.setEmail(form.getEmail());        // 이메일 설정

        String encodedPassword = passwordEncoder.encode(form.getPassword()); //password 암호화
        user.setPassword(encodedPassword); // 비밀번호 설정

        user.setEnable(true); // default : 활성화

        Role role = new Role();
        role.setId(1L);// default 권한부여 : ROLE_USER
        user.getRoles().add(role);

        validateDuplicatedMember(user); // 중복 회원 검증
        return userRepository.save(user);
    }

    private void validateDuplicatedMember(User user) {
        // 중복 회원 검증 메서드
        User findUsers = userRepository.findByUsername(user.getUsername());
        if (findUsers != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }

    public List<User> findMembers() {
        return userRepository.findAll();
    }

    //--단일 회원 조회--//
//    public User findOne(User user) {
//        return userRepository.findOne(user.getId());
//    }
}
