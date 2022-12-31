package com.Easy.webcarpool.controller;

import com.Easy.webcarpool.dto.ProfileResponseDto;
import com.Easy.webcarpool.dto.ProfileUpdateRequestDto;
import com.Easy.webcarpool.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserInfoController {

    Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 내정보
     * */
    @GetMapping("/info")
    public String userInfo(Model model, Authentication authentication) {
        // 현재 로그인한 사용자의 정보로 조회
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ProfileResponseDto profileResponseDto = userInfoService.getUserProfile(userDetails.getUsername());
        model.addAttribute("profile", profileResponseDto);
        return "info/info";
    }

    /**
     * 내정보 수정
     */
    @GetMapping("/info/update")
//    @RequestMapping(value = "info/update", method = {RequestMethod.GET, RequestMethod.POST})
    public String userInfoUpdate(Model model, Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ProfileResponseDto profileResponseDto = userInfoService.getUserProfile(userDetails.getUsername());

        model.addAttribute("profile", profileResponseDto);
//        model.addAttribute("profileUpdateForm",new ProfileUpdateRequestDto());

        return "/info/info_update";
    }

    @PostMapping("/info/update")
    public String userInfoUpdate(@RequestBody ProfileUpdateRequestDto profileUpdateRequestDto) {
//        if (result.hasErrors()) {
//            return "/info/info";
//        }

//        model.addAttribute("updateForm", new ProfileUpdateRequestDto());
        userInfoService.updateProfile(profileUpdateRequestDto);
        logger.debug("getAddress : {}", profileUpdateRequestDto.getAddress());
        logger.debug("getBirth : {}", profileUpdateRequestDto.getBirth());
        logger.debug("getIntroduce : {}", profileUpdateRequestDto.getIntroduce());

        return "redirect:/info/info_update";
    }

}
