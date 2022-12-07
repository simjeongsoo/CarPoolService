package com.Easy.webcarpool.config;

import com.Easy.webcarpool.jwt.JwtAccessDeniedHandler;
import com.Easy.webcarpool.jwt.JwtAuthenticationEntryPoint;
import com.Easy.webcarpool.jwt.JwtSecurityConfig;
import com.Easy.webcarpool.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity // Spring Security 설정 활성화
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) // @PreAuthorize 어노테이션을 메소드 단위로 추가하기 위해 적용
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    // application.properties 정의된 datasource 사용
    @Autowired
    private DataSource dataSource;

    @Bean //password 암호화
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /* public PasswordEncoder passwordEncoder() 했을때 BeanCurrentlyInCreationException 오류 발생
    -> inner class의 경우 사용시 static 키워드로 내부클래으세어 바로 사용할 수 있도록 하게 해줌
    * */

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        //--h2-console 하위의 모든 요청들과 파비콘 관련 요청은 Spring Security 로직을 수행하지 않고 접근설정--//
        return web -> web
                .ignoring()
                .antMatchers("/h2-console/**", "/favicon.ico");
    }

//=========================================================================================
    /**
     * 접근
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin();
        //http 접근 보안설정
        http
                // token을 사용하는 방식이기 때문에 csrf를 disable
                .csrf().disable()

                // Exception을 핸들링 할때 만들어둔 클래스 추가
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 접근 권한 설정
                .and()
                .authorizeRequests()
                .antMatchers("/api/*","/", "/account/register", "/account/*", "/css/**", "/api/**", "/assets/**", "/chat/**").permitAll()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/account/login") // 사용자 정의 로그인 페이지
//                .defaultSuccessUrl("/index") // 로그인 성공 후 이동 페이지
                .successHandler(new LoginSuccessHandler()).permitAll() // 로그인 성공 후 핸들러

                .and()
                .logout().permitAll()

                // JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig 클래스 적용
                .and()
                .apply(new JwtSecurityConfig(tokenProvider));
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
