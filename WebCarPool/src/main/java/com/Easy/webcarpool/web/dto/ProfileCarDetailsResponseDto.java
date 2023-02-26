package com.Easy.webcarpool.web.dto;

import com.Easy.webcarpool.web.model.UserCar;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class ProfileCarDetailsResponseDto {
    private String licenceOrgNm;        // 운전면허 이미지 원본 이름
    private String licenceSavedNm;      // 운전면허 이미지 저장 이름
    private String licenceSavedPath;    // 운전면허 이미지 저장 경로

    private String carNum;              // 차량 번호
    private String carType;             // 차종
    private String carColor;            // 차량 색상

    private String carImgOrgNm;         // 차량 원본 이름
    private String carImgSavedNm;       // 차량 저장 이름
    private String carImgSavedPath;     // 차량 저장 경로

    @Builder
    public ProfileCarDetailsResponseDto(String licenceOrgNm, String licenceSavedNm, String licenceSavedPath, String carNum, String carType, String carColor, String carImgOrgNm, String carImgSavedNm, String carImgSavedPath) {
        this.licenceOrgNm = licenceOrgNm;
        this.licenceSavedNm = licenceSavedNm;
        this.licenceSavedPath = licenceSavedPath;
        this.carNum = carNum;
        this.carType = carType;
        this.carColor = carColor;
        this.carImgOrgNm = carImgOrgNm;
        this.carImgSavedNm = carImgSavedNm;
        this.carImgSavedPath = carImgSavedPath;
    }

    public Optional<ProfileCarDetailsResponseDto> toDto(UserCar entity) {
        // entity to dto
        return Optional.ofNullable(ProfileCarDetailsResponseDto.builder()
                .licenceOrgNm(entity.getLicenceOrgNm())
                .licenceSavedNm(entity.getLicenceSavedNm())
                .licenceSavedPath(entity.getLicenceSavedPath())
                .carNum(entity.getCarNum())
                .carType(entity.getCarType())
                .carColor(entity.getCarColor())
                .carImgOrgNm(entity.getCarImgOrgNm())
                .carImgSavedNm(entity.getCarImgSavedNm())
                .carImgSavedPath(entity.getCarImgSavedPath())
                .build());
    }

}
