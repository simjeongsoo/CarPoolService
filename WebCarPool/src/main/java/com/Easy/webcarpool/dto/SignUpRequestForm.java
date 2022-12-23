package com.Easy.webcarpool.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SignUpRequestForm {

    @NotEmpty(message = "이름은 필수입니다.")
    private String realname;

    @NotEmpty(message = "아이디는 필수입니다.")
    private String username;

    @Email
    @NotNull(message = "이메일은 필수입니다.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password;
}
