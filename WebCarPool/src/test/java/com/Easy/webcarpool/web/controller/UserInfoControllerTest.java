package com.Easy.webcarpool.web.controller;

import com.Easy.webcarpool.web.domain.User;
import com.Easy.webcarpool.web.dto.ProfileCarDetailsResponseDto;
import com.Easy.webcarpool.web.dto.ProfileResponseDto;
import com.Easy.webcarpool.web.service.user.UserInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserInfoControllerTest {

    @Mock
    UserInfoService userInfoService;

    @Mock
    Model model;

    @InjectMocks
    UserInfoController userInfoController;

    MockHttpSession mockHttpSession;
    MockHttpServletRequest mockHttpServletRequest;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        mockHttpSession = new MockHttpSession();
        mockHttpServletRequest = new MockHttpServletRequest();
    }

    @Test
    void testUserInfo() throws Exception {
        UserDetails userDetails = new org.springframework.security.core.userdetails.User("testUser", "testPassword",
                null);
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null);

        ProfileResponseDto profileResponseDto = new ProfileResponseDto();
        ProfileCarDetailsResponseDto profileCarDetailsResponseDto = new ProfileCarDetailsResponseDto();

        when(userInfoService.getUserProfile(anyString())).thenReturn(profileResponseDto);
        when(userInfoService.getUserCarDetails(anyString())).thenReturn(Optional.of(profileCarDetailsResponseDto));

        userInfoController.userInfo(model, auth);
    }

    @Test
    void testGetProfileImage() throws Exception {
        String username = "testUser";
        User user = new User();
        user.setProfileImgSavedPath("testPath");

        when(userInfoService.getUserProfileImg(username)).thenReturn(Optional.of(user));

        userInfoController.getProfileImage(username);
    }

}