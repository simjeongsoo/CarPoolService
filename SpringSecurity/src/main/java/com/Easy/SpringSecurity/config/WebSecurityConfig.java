package com.Easy.SpringSecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // application.properties 정의된 datasource 사용
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //http 접근 보안설정
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/account/register","/css/**","/api/**","/assets/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/account/login")
                .defaultSuccessUrl("/index")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }
//.defaultSuccessUrl("/index") 로그인 성공후 연결 url



    //https://www.baeldung.com/spring-security-jdbc-authentication
    //spring 인증처리
    //jdbc 이용한 사용자정보, 권한정보 가져오는 query 작성
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
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
     * Authentication 로그인 처리 == usersByUsernameQuery
     * Authorization 권한 처리 == authoritiesByUsernameQuery
     * */


    @Bean //password 암호화
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /* public PasswordEncoder passwordEncoder() 했을때 BeanCurrentlyInCreationException 오류 발생
    -> inner class의 경우 사용시 static 키워드로 내부클래으세어 바로 사용할 수 있도록 하게 해줌

    * */

}
