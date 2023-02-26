package com.Easy.webcarpool.web.dto;

import com.Easy.webcarpool.web.model.User;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
//@RequiredArgsConstructor
public class ProfileUpdateRequestDto {

    private String address;   // 주소
//    @Size(max = 8)
    private String birth;     // 생년월일
//    @Size(max = 100)
    private String introduce; // 자기소개

//    private MultipartFile profileImg; // 프로필 사진

    @Builder
    public ProfileUpdateRequestDto(String address, String birth, String introduce) {
        this.address = address;
        this.birth = birth;
        this.introduce = introduce;
//        this.profileImg = profileImg;
    }

    public User toEntity() {
        return User.builder()
                .address(address)
                .birth(birth)
                .introduce(introduce)
                .build();
    }
}