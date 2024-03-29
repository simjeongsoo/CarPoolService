package com.Easy.webcarpool.web.service.user;

import com.Easy.webcarpool.web.dto.SignUpRequestForm;
import com.Easy.webcarpool.web.domain.Role;
import com.Easy.webcarpool.web.domain.User;
import com.Easy.webcarpool.web.domain.UserCar;
import com.Easy.webcarpool.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    //--user 관련 비즈니스 로직--//

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     * */
    @Transactional
    public User join(SignUpRequestForm form){

        User user = new User();
        UserCar userCar = new UserCar();
        user.setRealname(form.getRealname()); // 실명 설정
        user.setUsername(form.getUsername()); // 아이디 설정
        user.setEmail(form.getEmail());       // 이메일 설정
        user.setUserCar(userCar);             // 차량정보 설정

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
//        User findUsers = userRepository.findByUsername(user.getUsername());
        if (userRepository != null) {
            userRepository.findByUsername(user.getUsername()).ifPresent(user1 -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
        }else {
            throw new IllegalStateException("userRepository is null");
        }

        /*if (findUsers != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }*/
    }

    //--단일 회원 조회--//
    public Optional<User> findOne(Long userId) {
        return userRepository.findById(userId);
    }
}
