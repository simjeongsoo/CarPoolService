package com.spring.SpringCRUD;

import com.spring.SpringCRUD.repository.MemberRepository;
import com.spring.SpringCRUD.repository.MemoryMemberRepository;
import com.spring.SpringCRUD.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
