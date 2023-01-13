package com.Easy.webcarpool.service;

import com.Easy.webcarpool.dto.ProfileCarDetailsResponseDto;
import com.Easy.webcarpool.dto.ProfileResponseDto;
import com.Easy.webcarpool.dto.ProfileUpdateRequestDto;
import com.Easy.webcarpool.model.User;
import com.Easy.webcarpool.model.UserCar;
import com.Easy.webcarpool.repository.UserCarRepository;
import com.Easy.webcarpool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.monitor.FileEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserInfoService {

    @Value("${profileImg.path}")
    private String fileDir;

    private final UserRepository userRepository;
    private final UserCarRepository userCarRepository;

    /**
     * 프로필 조회
     * */
    // 사용자 정보 조회
    public ProfileResponseDto getUserProfile(String username) {

//        User findByUsername = userRepository.findByUsername(username);
        User findByUsername = userRepository.findByUsername(username).get();
        return new ProfileResponseDto().toDto(findByUsername);

    }

    // 차량조회
    public ProfileCarDetailsResponseDto getUserCarDetails(String username) {

//        Long userId = userRepository.findByUsername(username).getId();
        Long userId = userRepository.findByUsername(username).get().getId();
        UserCar userCarDetails = userCarRepository.findByUser_Id(userId);

        return new ProfileCarDetailsResponseDto().toDto(userCarDetails);

    }

    /**
     * 프로필 업데이트
     * */
    @Transactional
    public void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto , String username) {

//        User updateProfile = userRepository.findByUsername(username); // dirty checking
        User updateProfile = userRepository.findByUsername(username).get();
        updateProfile.updateProfile(profileUpdateRequestDto.getAddress(), profileUpdateRequestDto.getBirth(), profileUpdateRequestDto.getIntroduce());

    }

    /**
     * 프로필 이미지 조회
     * */
    public Optional<User> getUserProfileImg(String username) {
//        return userRepository.findByUsername(username);
        return userRepository.findByUsername(username);
    }

    /**
     * 프로필 이미지 저장
     */
    @Transactional
    public Long saveProfileImg(MultipartFile files, String username) throws IOException {

        // 프로필 이미지가 존재하면 삭제후 업데이트 로직 추가 예정

        if (files.isEmpty()) {
            return null;
        }

        // 원래 파일 이름 추출
        String origName = files.getOriginalFilename();

        // 파일 이름으로 쓸 uuid 생성
        String uuid = UUID.randomUUID().toString();

        // 확장자 추출(ex : .png)
        String extension = origName.substring(origName.lastIndexOf("."));

        // uuid와 확장자 결합
        String savedName = uuid + extension;

        // 파일을 불러올 때 사용할 파일 경로
        String savedPath = fileDir + savedName;

        // 파일 엔티티 생성
        User file = User.builder()
                .profileImgOrgNm(origName)
                .profileImgSavedNm(savedName)
                .profileImgSavedPath(savedPath)
                .build();

        // 실제로 로컬에 uuid를 파일명으로 저장
        files.transferTo(new File(savedPath));

        // 데이터베이스에 파일 정보 저장
//        User updateProfile = userRepository.findByUsername(username); // dirty checking
        User updateProfile = userRepository.findByUsername(username).get();

        updateProfile.updateProfileImg(
                file.getProfileImgOrgNm()
                ,file.getProfileImgSavedNm()
                ,file.getProfileImgSavedPath()
        );

        User savedProfile = userRepository.save(updateProfile);

        return savedProfile.getId();
    }

    /**
     * 프로필 이미지 삭제
     */
    public void deleteProfile() {
        // to be added ...
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }
}
