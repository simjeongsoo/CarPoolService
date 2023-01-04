package com.Easy.webcarpool.controller;

import com.Easy.webcarpool.dto.ProfileResponseDto;
import com.Easy.webcarpool.dto.ProfileUpdateRequestDto;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

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
    public String userInfoUpdate(@RequestBody ProfileUpdateRequestDto profileUpdateRequestDto, Authentication authentication) {

//        if (result.hasErrors()) {
//            return "/info/info";
//        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        userInfoService.updateProfile(profileUpdateRequestDto, username);

        return "redirect:/info/info_update";

    }

    /**
     * 이미지 업로드 (demo)
    * */
    @PostMapping("/info/update")
    public String uploadFile(HttpSession session, Model model, @RequestParam(name = "file", required = false) MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String uuid = UUID.randomUUID().toString() + ".jpg";
            File converFile = new File(savePath, uuid);
            file.transferTo(converFile);

        }

        return "redirect:/";
    }
}
