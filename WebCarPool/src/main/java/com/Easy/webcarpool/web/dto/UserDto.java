package com.Easy.webcarpool.web.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    //--회원가입시 사용할 필수 정보 Dto--//

    @NotNull
    @Size(min = 3, max = 50)
    private String membername; // 유저 이름

    @NotNull
    @Size(min = 3, max = 50)
    private String email;   // 이메일

    @NotNull
    private String password; // 비밀번호


}
