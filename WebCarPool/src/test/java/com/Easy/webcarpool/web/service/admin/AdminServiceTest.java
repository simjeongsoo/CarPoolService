package com.Easy.webcarpool.web.service.admin;

import com.Easy.webcarpool.web.domain.User;
import com.Easy.webcarpool.web.repository.RoleRepository;
import com.Easy.webcarpool.web.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AdminService adminService;

//    @BeforeEach
//    public void initMocks() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    @DisplayName("전체 사용자 조회 테스트") // test passed
    void findAllUsers() throws Exception{
        // given
        List<User> users = new ArrayList<>();

        User user1 = User.builder()
                .realname("realname")
                .username("username")
                .email("test@gmail.com")
                .build();

        User user2 = User.builder()
                .realname("realname2")
                .username("username2")
                .email("test2@gmail.com")
                .build();

        users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        // when
        List<User> result = adminService.findAllUsers();

        // then
        assertEquals(users.size(), result.size());
        assertEquals(users.get(0), result.get(0));
        assertEquals(users.get(1), result.get(1));
    }

}