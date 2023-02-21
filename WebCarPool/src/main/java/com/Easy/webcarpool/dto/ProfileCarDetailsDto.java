package com.Easy.webcarpool.dto;

import com.Easy.webcarpool.model.UserCar;
import lombok.*;

import java.util.Optional;

public class ProfileCarDetailsDto {
    //--request, response DTO 클래스를 하나로 묶어 InnerStaticClass로 한 번에 관리하도록 리펙토링--//

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private String carNum;              // 차량 번호
        private String carType;             // 차종
        private String carColor;            // 차량 색상

        /* entity to dto */
        public UserCar toEntity() {
            return UserCar.builder()
                    .carNum(carNum)
                    .carType(carType)
                    .carColor(carColor)
                    .build();
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private String carNum;              // 차량 번호
        private String carType;             // 차종
        private String carColor;            // 차량 색상
        private String licenceOrgNm;        // 운전면허 이미지 원본 이름
        private String licenceSavedNm;      // 운전면허 이미지 저장 이름
        private String licenceSavedPath;    // 운전면허 이미지 저장 경로

        private String carImgOrgNm;         // 차량 원본 이름
        private String carImgSavedNm;       // 차량 저장 이름
        private String carImgSavedPath;     // 차량 저장 경로


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
}
