package com.Easy.webcarpool.web.controller;

import com.Easy.webcarpool.web.dto.ProfileCarDetailsRequestDto;
import com.Easy.webcarpool.web.dto.ProfileCarDetailsResponseDto;
import com.Easy.webcarpool.web.dto.ProfileResponseDto;
import com.Easy.webcarpool.web.dto.ProfileUpdateRequestDto;
import com.Easy.webcarpool.web.model.User;
import com.Easy.webcarpool.web.service.user.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Controller
@PropertySource("classpath:/application.properties")
public class UserInfoController {

    Logger logger = LoggerFactory.getLogger(UserInfoController.class);
    private final UserInfoService userInfoService;

    @Autowired
    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Value("${profileImg.path}")
    private String savePath; // 업로드 파일 저장 위치

    /**
     * 내정보
     */
    @GetMapping("/info")
    public String userInfo(Model model, Authentication authentication) throws IOException {
        // 현재 로그인한 사용자의 정보
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String currentUsername = userDetails.getUsername();

        // 사용자 정보 조회
        ProfileResponseDto profileResponseDto = userInfoService.getUserProfile(currentUsername);
        model.addAttribute("profile", profileResponseDto);

        // 차량 정보 조회
        ProfileCarDetailsResponseDto userCarDetails = userInfoService.getUserCarDetails(currentUsername).get();
        model.addAttribute("carDetails", userCarDetails);

        return "info/info";
    }

    // 프로필 이미지 출력 api
    @GetMapping("/info/{username}")
    @ResponseBody
    public UrlResource getProfileImage(@PathVariable(value = "username",required = false) String username) throws IOException{

        String defaultUserProfilePath = "/Users/simjeongsu/Documents/CarPoolService/images/profiledefault.png";

        try {
            Optional<User> userProfileImg = userInfoService.getUserProfileImg(username);
            String profileImgSavedPath = userProfileImg.get().getProfileImgSavedPath();
            if (profileImgSavedPath != null) {
                return new UrlResource("file:" + profileImgSavedPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchElementException e) {

        }

        return new UrlResource("file:" + defaultUserProfilePath);
    }

//======================================================================================================================

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

        // 차량 정보 조회
        ProfileCarDetailsResponseDto userCarDetails = userInfoService.getUserCarDetails(currentUsername).get();
        model.addAttribute("carDetails", userCarDetails);

        return "/info/info_update";
    }

    @PostMapping(value = "/info/update", consumes = "multipart/form-data")
    public String userInfoUpdate(@RequestPart(value = "key", required = false) ProfileUpdateRequestDto profileUpdateRequestDto,
                                 @RequestPart(value = "file", required = false) MultipartFile file,
                                 @RequestPart(value = "key2", required = false) ProfileCarDetailsRequestDto profileCarDetailsRequestDto,
                                 @RequestPart(value = "licence", required = false) MultipartFile licence,
                                 @RequestPart(value = "carImg", required = false) MultipartFile carImg,
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
            userInfoService.saveProfileImg(file, currentUsername);
        } else {
            logger.info("파일 없음");
        }

        if (licence.isEmpty() || carImg.isEmpty()) {
            logger.info("파일 없음");
        } else {
            userInfoService.saveCarDetails(currentUsername, profileCarDetailsRequestDto, licence, carImg);
        }


        return "redirect:/info/info_update";
    }

    // 운전면허,차량 이미지 출력 api
    @GetMapping("/info/{imgchoice}/{username}")
    @ResponseBody
    public UrlResource getCarDetailsImage(
            @PathVariable(value = "imgchoice", required = false) String imgChoice,
            @PathVariable(value = "username",required = false) String username) throws IOException{

        String defaultCarDetailsPath = "/Users/simjeongsu/Documents/CarPoolService/images/cardetails/default.png";

        try {
            Optional<User> userProfileImg = userInfoService.getUserProfileImg(username);
            String licenceSavedPath = userProfileImg.get().getUserCar().getLicenceSavedPath();
            String carImgSavedPath = userProfileImg.get().getUserCar().getCarImgSavedPath();

            if (licenceSavedPath != null && carImgSavedPath != null) {
                if (Objects.equals(imgChoice, "licence")) {
                    return new UrlResource("file:" + licenceSavedPath);
                } else if (Objects.equals(imgChoice, "carimg")) {
                    return new UrlResource("file:" + carImgSavedPath);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchElementException e) {

        }

        return new UrlResource("file:" + defaultCarDetailsPath);
    }

//======================================================================================================================

    /**
     * 업로드 파일 삭제
     */
    @GetMapping("info/delete/{username}")
    public ResponseEntity<Boolean> removeUserInfoImg(@PathVariable("username") String username) {
        userInfoService.deleteImgFile(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
