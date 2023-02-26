package com.Easy.webcarpool.web.dto;

import com.Easy.webcarpool.web.domain.User;
import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
//@RequiredArgsConstructor
public class ProfileResponseDto {

    private String realname; // 실명
    private String username; // 닉네임(아이디)
    private String email;    // 이메일
    private String address;  // 주소
    private boolean gender;  // 성별
    private String birth;    // 생년월일

    @Size(max = 100)
    private String introduce; // 자기소개

    @Builder
    public ProfileResponseDto(String realname, String username, String email, String address, boolean gender, String birth, String introduce) {
        this.realname = realname;
        this.username = username;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.birth = birth;
        this.introduce = introduce;
    }

    public ProfileResponseDto toDto(User entity) {
        // entity to dto
        return ProfileResponseDto.builder()
                .realname(entity.getRealname())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .gender(entity.isGender())
                .birth(entity.getBirth())
                .introduce(entity.getIntroduce())
                .build();
    }

}
