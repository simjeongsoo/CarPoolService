package com.Easy.webcarpool.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity // Spring Security 설정 활성화
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true  // @PreAuthorize 어노테이션을 메소드 단위로 추가하기 위해 적용
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private DataSource dataSource; // application.properties 정의된 datasource 사용
    @Bean
    public static PasswordEncoder passwordEncoder() {
        //password 암호화
        return new BCryptPasswordEncoder();
    }
//=========================================================================================
    /**
     * Spring Security config method
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin();
        //http 접근 보안설정
        http

                // token을 사용하는 방식이기 때문에 csrf를 disable
                .csrf().disable()
                // 접근 권한 설정
                .authorizeRequests()
                .antMatchers("/",
                        "/account/register",
                        "/account/*",
                        "/css/**",
                        "/api/**",
                        "/assets/**",
                        "/chat/**",
                        "/image/**",
                        "/uploadImg/**").permitAll()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/account/login") // 사용자 정의 로그인 페이지
//                .defaultSuccessUrl("/index") // 로그인 성공 후 이동 페이지
                .successHandler(new LoginSuccessHandler()).permitAll() // 로그인 성공 후 핸들러

                .and()
                .logout().permitAll();
    }

//=========================================================================================
    //https://www.baeldung.com/spring-security-jdbc-authentication
    //spring 인증처리
    //jdbc 이용한 사용자정보, 권한정보 가져오는 query 작성
    /**
     * 로그인 입증
     * */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { // AuthenticationManagerBuilder:  인증 매니저 생성 빌더

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select username, password, enable "
                        + "from user "
                        + "where username = ?")
                .authoritiesByUsernameQuery("select u.username, r.name "
                        + "from user_role ur inner join user u on ur.user_id = u.id "
                        + "inner join role r on ur.role_id = r.id "
                        + "where u.username = ?");
    }
    /*
     * Authentication 로그인 처리 : usersByUsernameQuery
     * Authorization 권한 처리 : authoritiesByUsernameQuery
     * */
//=========================================================================================

} // end class
