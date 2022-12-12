package com.Easy.webcarpool.jwt.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    //--TokenProvider, JwtFilter 를 SecurityConfig에 적용할 때 사용할 클래스--//

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // JwtFilter 를 통해
        JwtAuthenticationFilter customFilter = new JwtAuthenticationFilter();
        // Security 로직에 필터 등록
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
