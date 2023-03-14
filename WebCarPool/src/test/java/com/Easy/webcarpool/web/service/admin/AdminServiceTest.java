package com.Easy.webcarpool.web.service.admin;

import com.Easy.webcarpool.web.domain.Role;
import com.Easy.webcarpool.web.domain.User;
import com.Easy.webcarpool.web.repository.RoleRepository;
import com.Easy.webcarpool.web.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

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

    @Test
    @DisplayName("'ROLE_USER' 조회")
    void findUserList() throws Exception{
        //given
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


        List<Role> userRoles = new ArrayList<>();
        userRoles.add(Role.builder().id(1L).name("ROLE_USER").build());
        userRoles.add(Role.builder().id(2L).name("ROLE_ADMIN").build());

        user1.setRoles(userRoles);
        user2.setRoles(userRoles);

        users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1), Optional.of(user2));

        // when
        List<User> userList = adminService.findUserList();

        // Then
        assertThat(userList).hasSize(2);
        assertThat(userList).contains(user1, user2);

    }

}