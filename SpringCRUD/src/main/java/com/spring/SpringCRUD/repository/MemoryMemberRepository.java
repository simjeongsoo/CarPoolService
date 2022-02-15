package com.spring.SpringCRUD.repository;

import com.spring.SpringCRUD.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

//회원 리포지토리 메모리 구현체
@Repository
public class MemoryMemberRepository implements MemberRepository{

      //동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L; // 키값 생성 시퀀스

    @Override
    public Member save(Member member) {
        member.setId(++sequence); // 스토어에 넣기전에 id값 세팅
        store.put(member.getId(), member); // store에 저장
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // 널값이 반활될 경우때문에 optional로 감싸서 반환
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name)) // parm으로 넘어온 name과 store에 있는 name을 필터링
                .findAny(); // 일치하면 반환, 없으면 optional로 null 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    } // MemberRepository 인터페이스 상속

    public void clearStore(){
        store.clear();
    }
}
