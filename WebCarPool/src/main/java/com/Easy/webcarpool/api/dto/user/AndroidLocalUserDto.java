package com.Easy.webcarpool.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AndroidLocalUserDto {
    private String nickname;
    private String email;
    private String token;
    private String gender;
    private Float rate;
    private Boolean driverAuthentication;
    private String fcmToken;
}
