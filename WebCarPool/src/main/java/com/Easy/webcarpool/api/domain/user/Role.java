package com.Easy.webcarpool.api.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    //Spring Security 사용시 권한 코드 앞에 ROLE필요
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반사용자"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
}
