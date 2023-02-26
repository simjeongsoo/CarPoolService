package com.Easy.webcarpool.web.dto;

import com.Easy.webcarpool.web.model.UserCar;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileCarDetailsRequestDto {

    private String carNum;              // 차량 번호
    private String carType;             // 차종
    private String carColor;            // 차량 색상

    @Builder
    public ProfileCarDetailsRequestDto(String carNum, String carType, String carColor) {
        this.carNum = carNum;
        this.carType = carType;
        this.carColor = carColor;
    }

    public UserCar toEntity() {
        return UserCar.builder()
                .carNum(carNum)
                .carType(carType)
                .carColor(carColor)
                .build();
    }

}
