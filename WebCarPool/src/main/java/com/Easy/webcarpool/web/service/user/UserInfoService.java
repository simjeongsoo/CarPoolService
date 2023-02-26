package com.Easy.webcarpool.web.service.user;

import com.Easy.webcarpool.web.dto.ProfileCarDetailsRequestDto;
import com.Easy.webcarpool.web.dto.ProfileCarDetailsResponseDto;
import com.Easy.webcarpool.web.dto.ProfileResponseDto;
import com.Easy.webcarpool.web.dto.ProfileUpdateRequestDto;
import com.Easy.webcarpool.web.model.User;
import com.Easy.webcarpool.web.model.UserCar;
import com.Easy.webcarpool.web.repository.UserCarRepository;
import com.Easy.webcarpool.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserInfoService {

    @Value("${profileImg.path}")
    private String fileDir;

    @Value("${carDetail.path}")
    private String carFileDir;

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
    public Optional<ProfileCarDetailsResponseDto> getUserCarDetails(String username) {

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
    @Transactional
    public void deleteImgFile(String username) {

        String profileImgSavedNm = userRepository.findByUsername(username).get().getProfileImgSavedNm(); // 저장이름
        String file = fileDir + profileImgSavedNm; // 저장경로 + 저장이름
        // 원본 파일 삭제
        try {
            // 파일 삭제
            File deleteFile = new File(file);
            if (deleteFile.exists()) deleteFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // DB 데이터 초기화
        userRepository.deleteImageDetailsByUsername(username);


    }

    /**
     * 운전면허, 차량사진 저장
     */
    @Transactional
    public Long saveCarDetails(String username, ProfileCarDetailsRequestDto requestDto, MultipartFile licence, MultipartFile carImg) throws IOException {
        if (licence.isEmpty() && carImg.isEmpty()) {
            return null;
        }
        // 원래 파일 이름 추출
        String licenceOrigName = licence.getOriginalFilename();
        String carImgOrigName = carImg.getOriginalFilename();

        // 파일 이름으로 쓸 uuid 생성
        String licenceUUID = UUID.randomUUID().toString();
        String carImgUUID = UUID.randomUUID().toString();

        // 확장자 추출(ex : .png)
        String licenceExtension = licenceOrigName.substring(licenceOrigName.lastIndexOf("."));
        String carImgExtension = carImgOrigName.substring(carImgOrigName.lastIndexOf("."));

        // uuid와 확장자 결합
        String licenceSavedName = licenceUUID + licenceExtension;
        String carImgSavedName = carImgUUID + carImgExtension;

        // 파일을 불러올 때 사용할 파일 경로
        String licenceSavedPath = carFileDir + licenceSavedName;
        String carImgSavedPath = carFileDir + carImgSavedName;


        // 파일 엔티티 생성
        UserCar file = UserCar.builder()
                .licenceOrgNm(licenceOrigName)
                .licenceSavedNm(licenceSavedName)
                .licenceSavedPath(licenceSavedPath)
                .carNum(requestDto.getCarNum())
                .carType(requestDto.getCarType())
                .carColor(requestDto.getCarColor())
                .carImgOrgNm(carImgOrigName)
                .carImgSavedNm(carImgSavedName)
                .carImgSavedPath(carImgSavedPath)
                .build();

        // 실제로 로컬에 uuid를 파일명으로 저장
        licence.transferTo(new File(licenceSavedPath));
        carImg.transferTo(new File(carImgSavedPath));

        // 데이터베이스에 파일 정보 저장
        UserCar userCar = userRepository.findByUsername(username).get().getUserCar();

        userCar.updateCarDetails(
                file.getLicenceOrgNm(),
                file.getLicenceSavedNm(),
                file.getLicenceSavedPath(),
                file.getCarNum(),
                file.getCarType(),
                file.getCarColor(),
                file.getCarImgOrgNm(),
                file.getCarImgSavedNm(),
                file.getCarImgSavedPath()
        );

//        User savedProfile = userRepository.save(updateProfile);
        UserCar save = userCarRepository.save(userCar);

        return save.getId();
    }
}
