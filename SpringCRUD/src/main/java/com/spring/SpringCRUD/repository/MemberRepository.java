package com.spring.SpringCRUD.repository;

import com.spring.SpringCRUD.domain.Member;

import java.util.List;
import java.util.Optional;

//회원 리포지토리 인터페이스
public interface MemberRepository {
    Member save(Member member); // 회원을 저장하면 저장된 회원이 반환
    Optional<Member> findById(Long id); //id로 회원을 찾음
    Optional<Member> findByName(String name); // name 으로 회원을 찾음
    List<Member> findAll(); // 저장된 리스트 전체 반환


}
