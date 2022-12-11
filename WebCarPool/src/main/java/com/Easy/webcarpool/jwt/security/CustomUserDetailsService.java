package com.Easy.webcarpool.jwt.security;

import com.Easy.webcarpool.jwt.model.Member;
import com.Easy.webcarpool.jwt.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    //--Spring Security에서 중요한 부분중 하나인 UserDetailsService를 구현한 클래스--//

    @Autowired
    MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // Client가 사용자 이름 또는 이메일로 로그인하도록 허용
        Member member = memberRepository.findByMembernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("이름 또는 이메일이 있는 사용자를 찾을 수 없습니다. {} : " + usernameOrEmail));

        return UserPrincipal.create(member);

//        return memberRepository.findOneWithAuthoritiesByMembername(username)
//                .map(member -> createMember(username, member))
//                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));

    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("ID가 있는 사용자를 찾을 수 없습니다. id : " + id));

        return UserPrincipal.create(member);
    }

    /*
    // UserPrincipal 에서 수행
    private User createMember(String username, Member member) {

        if (!member.isActivated()) {
            throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
        }

        List<GrantedAuthority> grantedAuthorities = member.getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().toString()))
                .collect(Collectors.toList());

        return new User(
                member.getEmail(),
                member.getPassword(),
                grantedAuthorities
        );

    }*/
}
