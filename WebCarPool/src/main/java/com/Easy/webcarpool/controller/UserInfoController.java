package com.Easy.webcarpool.controller;

import com.Easy.webcarpool.dto.ProfileResponseDto;
import com.Easy.webcarpool.dto.ProfileUpdateRequestDto;
import com.Easy.webcarpool.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserInfoController {

    Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserService userService;

    /**
     * 현재 사용자 프로필 조회
     * */
    @GetMapping("/info")
    public String userInfo(Model model, Authentication authentication) {
        // 현재 로그인한 사용자의 정보로 조회
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ProfileResponseDto profileResponseDto = userService.getUserProfile(userDetails.getUsername());
        model.addAttribute("profile", profileResponseDto);
        return "info/info";
    }

    /**
     * 사용자 프로필 업데이트
     */
    @GetMapping("/info/update")
    public String userInfoUpdate(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ProfileResponseDto profileResponseDto = userService.getUserProfile(userDetails.getUsername());
        model.addAttribute("profile", profileResponseDto);
        return "/info/info_update";
    }

    @PostMapping("/info/update")
    public String userInfo(@Valid ProfileUpdateRequestDto profileUpdateRequestDto) {
//        if (result.hasErrors()) {
//            return "/info/info";
//        }
//        userService.updateProfile(profileUpdateRequestDto);


        logger.debug("profileUpdateRequestDto : {}", profileUpdateRequestDto.getAddress());
        logger.debug("profileUpdateRequestDto : {}", profileUpdateRequestDto.getBirth());
        logger.debug("profileUpdateRequestDto : {}", profileUpdateRequestDto.getIntroduce());
        return "/info/info_update";
    }

}
