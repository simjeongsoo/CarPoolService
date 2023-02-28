package com.Easy.webcarpool.web.service.user;

import com.Easy.webcarpool.web.domain.User;
import com.Easy.webcarpool.web.dto.SignUpRequestForm;
import com.Easy.webcarpool.web.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserService userService;

    @DisplayName("회원가입 테스트")
    @Test
    void join() {

        // given
        UserService userService = new UserService(userRepository, passwordEncoder);
        SignUpRequestForm form = new SignUpRequestForm();
        form.setRealname("RealName1");
        form.setUsername("UserName1");
        form.setEmail("test1@google.com");
        form.setPassword("password1");

        // when
        when(userRepository.findByUsername(any())).thenReturn(null);
        userService.join(form);

        // then
        assertEquals(1, userRepository.findAll().size());
    }

    @Test
    void testJoinDuplicateUser() {

        User user = new User();
        user.setUsername("test");

        userRepository.save(user);

        User duplicateUser = new User();
        duplicateUser.setUsername("test");

        SignUpRequestForm form = new SignUpRequestForm();
        form.setUsername(duplicateUser.getUsername());


        assertThrows(IllegalStateException.class, () -> {
            userService.join(form);
        });
    }

}