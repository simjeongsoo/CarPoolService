package com.spring.SpringCRUD.repository;

import com.spring.SpringCRUD.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach // 각 메서드가 끝나면 호출
    public void afterEach(){
        //각테스트가 끝나면 repository clear
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("saveTest"); // 테스트용 회원 정보 ㅓ장

        repository.save(member);

        Member result =  repository.findById(member.getId()).get(); // db에서 꺼낸 회원 정보
        //System.out.println("result = " + (result == member)); // 단순한 출력 방식
        //Assertions.assertEquals(member, result); // (junit)
        assertThat(member).isEqualTo(result); // (assertj) 같은지 비교
    }

    @Test
    public void findByName(){

        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);

    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
