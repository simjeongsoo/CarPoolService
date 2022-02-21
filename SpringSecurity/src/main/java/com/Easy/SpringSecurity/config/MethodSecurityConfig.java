package com.Easy.SpringSecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

// 권한에 맞는 API 호출을 위한 클래스
// 아무나 api 요청을 못 넣도록 설정
@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
}
