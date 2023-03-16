package com.Easy.webcarpool.web.service.user;

import com.Easy.webcarpool.web.domain.User;
import com.Easy.webcarpool.web.domain.UserCar;
import com.Easy.webcarpool.web.dto.ProfileCarDetailsResponseDto;
import com.Easy.webcarpool.web.dto.ProfileResponseDto;
import com.Easy.webcarpool.web.repository.UserCarRepository;
import com.Easy.webcarpool.web.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class UserInfoServiceTest {

    @InjectMocks
    private UserInfoService userInfoService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserCarRepository userCarRepository;

//    @BeforeEach
//    public void setUp() {
////        MockitoAnnotations.initMocks(this);
//        MockitoAnnotations.openMocks(this);
//        ReflectionTestUtils.setField(userInfoService, "fileDir", "./test/profileImg/");
//        ReflectionTestUtils.setField(userInfoService, "carFileDir", "./test/carImg/");
//    }

    @Test
    @DisplayName("getUserProfile 메소드 테스트")
    public void getUserProfile() {
        // given
        String username = "testuser";
        User user = User.builder().username("test").realname("realname").email("email@gmail.com").build();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // when
        ProfileResponseDto result = userInfoService.getUserProfile(username);

        // then
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    @DisplayName("getUserCarDetails 메소드 테스트")
    public void getUserCarDetails() {
        // given
        User user = new User();
        user.setId(1L);
        when(userRepository.findByUsername("sim")).thenReturn(Optional.of(user));


        UserCar userCar = UserCar.builder()
                .carColor("blue")
                .build();
        when(userCarRepository.findByUser_Id(1L)).thenReturn(userCar);

        // when
        Optional<ProfileCarDetailsResponseDto> result = userInfoService.getUserCarDetails("sim");

        // then
        assertTrue(result.isPresent());
        assertEquals("blue", result.get().getCarColor());
    }

}