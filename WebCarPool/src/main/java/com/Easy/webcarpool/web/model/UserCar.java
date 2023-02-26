package com.Easy.webcarpool.web.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert // insert시 null 인 필드값 제외(=@PrePersist)
public class UserCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    public UserCar(String licenceOrgNm, String licenceSavedNm, String licenceSavedPath, String carNum, String carType, String carColor, String carImgOrgNm, String carImgSavedNm, String carImgSavedPath) {
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

    /**
     * 연관관계 매핑
     */
    @OneToOne(mappedBy = "userCar",fetch = FetchType.LAZY)
    private User user; // 유저테이블과 1:1 양방향 매핑

    /**
     * 정보수정
     * dirty checking
     * */
    public void updateCarDetails(String licenceOrgNm, String licenceSavedNm, String licenceSavedPath, String carNum, String carType, String carColor, String carImgOrgNm, String carImgSavedNm, String carImgSavedPath) {
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
}
