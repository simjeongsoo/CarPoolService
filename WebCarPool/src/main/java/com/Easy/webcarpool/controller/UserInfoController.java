package com.Easy.webcarpool.controller;

import com.Easy.webcarpool.dto.ProfileResponseDto;
import com.Easy.webcarpool.dto.ProfileUpdateRequestDto;
import com.Easy.webcarpool.model.User;
import com.Easy.webcarpool.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@PropertySource("classpath:/application.properties")
public class UserInfoController {

    Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserInfoService userInfoService;

    @Value("${file.path}")
    private String savePath; // 업로드 파일 저장 위치

    /**
     * 내정보
     */
    @GetMapping("/info")
    public String userInfo(Model model, Authentication authentication) {
        // 현재 로그인한 사용자의 정보
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String currentUsername = userDetails.getUsername();

        // 사용자 정보 조회
        ProfileResponseDto profileResponseDto = userInfoService.getUserProfile(currentUsername);
        model.addAttribute("profile", profileResponseDto);

        // 사용자 프로필 이미지 조회
        User userProfileImg = userInfoService.getUserProfileImg(currentUsername);
        String profileImgSavedPath = userProfileImg.getProfileImgSavedPath();
        String profileImg = profileImgSavedPath.substring(72);
        model.addAttribute("profileImg",profileImg);
        // /Users/simjeongsu/Documents/CarPoolService/WebCarPool/src/main/resources/static/uploadImg/7dab7a56-7283-45a4-8e9a-af49453b0dd2.jpeg

        logger.debug("substring : {}", profileImg);


        return "info/info";
    }

    /**
     * 내정보 수정
     */
    @GetMapping("/info/update")
    public String userInfoUpdate(Model model, Authentication authentication) {
        // 현재 로그인한 사용자의 정보
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String currentUsername = userDetails.getUsername();

        // 사용자 정보 조회
        ProfileResponseDto profileResponseDto = userInfoService.getUserProfile(currentUsername);
        model.addAttribute("profile", profileResponseDto);

        // 사용자 프로필 이미지 조회
        User userProfileImg = userInfoService.getUserProfileImg(currentUsername);
        String profileImgSavedPath = userProfileImg.getProfileImgSavedPath();
        String profileImg = profileImgSavedPath.substring(79);
        model.addAttribute("profileImg",profileImg);

        return "/info/info_update";
    }

    @PostMapping(value = "/info/update", consumes = "multipart/form-data")
    public String userInfoUpdate(@RequestPart(value = "key", required = false) ProfileUpdateRequestDto profileUpdateRequestDto,
                                 @RequestPart(value = "file", required = false) MultipartFile file,
                                 Authentication authentication) throws IOException {

//        if (result.hasErrors()) {
//            return "/info/info";
//        }

        // 현재 로그인한 사용자의 정보
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String currentUsername = userDetails.getUsername();

        // 프로필 정보 업데이트시 실행
        if (profileUpdateRequestDto != null) {
            userInfoService.updateProfile(profileUpdateRequestDto, currentUsername);
        }

        // 프로필 이미지 업데이트시 실행
        if (file != null) {
            logger.info("getName : {}", file.getName());
            logger.info("getOriginalFilename : {}", file.getOriginalFilename());
            userInfoService.saveProfileImg(file, currentUsername);
        } else {
            logger.info("파일 없음");
        }

        return "redirect:/info/info_update";
    }
}
