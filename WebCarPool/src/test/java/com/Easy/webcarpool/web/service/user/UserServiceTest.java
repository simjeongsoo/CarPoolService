package com.Easy.webcarpool.web.service.user;

import com.Easy.webcarpool.web.domain.User;
import com.Easy.webcarpool.web.dto.SignUpRequestForm;
import com.Easy.webcarpool.web.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test // test passed
    @DisplayName("회원가입 테스트")
    public void join() {
        // given
        SignUpRequestForm form = new SignUpRequestForm();
        form.setRealname("test");
        form.setUsername("test123");
        form.setEmail("test@example.com");
        form.setPassword("password");

        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setRealname("test");
        expectedUser.setUsername("test123");
        expectedUser.setEmail("test@example.com");
        expectedUser.setPassword("encodedPassword");

        when(userRepository.findByUsername("test123")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        // when
        User actualUser = userService.join(form);

        verify(userRepository, times(1)).findByUsername("test123");
        verify(userRepository, times(1)).save(any(User.class));

        // then
        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getRealname(), actualUser.getRealname());
        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());
    }

    @Test // test passed
    @DisplayName("중복 회원 검증 테스트")
    public void joinWithDuplicatedUsername() {
        // given
        SignUpRequestForm form = new SignUpRequestForm();
        form.setRealname("test");
        form.setUsername("test123");
        form.setEmail("test@example.com");
        form.setPassword("password");

        User existingUser = new User();
        existingUser.setId(2L);
        existingUser.setRealname("test join");
        existingUser.setUsername("test123");
        existingUser.setEmail("testjoin@example.com");
        existingUser.setPassword("encodedPassword");

        // when
        when(userRepository.findByUsername("test123")).thenReturn(Optional.of(existingUser));

        try {
            userService.join(form);
        } catch (IllegalStateException e) {
            assertEquals("이미 존재하는 회원입니다.", e.getMessage());
        }

        // then
        verify(userRepository, times(1)).findByUsername("test123");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test // test passed
    @DisplayName("findOne 테스트")
    public void findOne() {
        // given
        Long userId = 1L;

        User expectedUser = new User();
        expectedUser.setId(userId);
        expectedUser.setRealname("test");
        expectedUser.setUsername("test123");
        expectedUser.setEmail("test@example.com");

        // when
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        Optional<User> actualUser = userService.findOne(userId);
        verify(userRepository, times(1)).findById(userId);

        // then
        assertEquals(expectedUser.getId(), actualUser.get().getId());
        assertEquals(expectedUser.getRealname(), actualUser.get().getRealname());
        assertEquals(expectedUser.getUsername(), actualUser.get().getUsername());
        assertEquals(expectedUser.getEmail(), actualUser.get().getEmail());
    }
}