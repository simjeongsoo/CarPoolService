package com.Easy.webcarpool.dto;

import com.Easy.webcarpool.model.User;
import lombok.*;

import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
//@RequiredArgsConstructor
public class ProfileUpdateRequestDto {

    private String address;   // 주소
    @Size(max = 8)
    private String birth;     // 생년월일
    @Size(max = 100)
    private String introduce; // 자기소개

    @Builder
    public ProfileUpdateRequestDto(String address, String birth, String introduce) {
        this.address = address;
        this.birth = birth;
        this.introduce = introduce;
    }

    public User toEntity() {
        return User.builder()
                .address(address)
                .birth(birth)
                .introduce(introduce)
                .build();
    }
}