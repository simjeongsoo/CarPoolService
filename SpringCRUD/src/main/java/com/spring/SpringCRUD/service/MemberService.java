package com.spring.SpringCRUD.service;

import com.spring.SpringCRUD.domain.Member;
import com.spring.SpringCRUD.repository.MemberRepository;
import com.spring.SpringCRUD.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//회원 서비스
@Service // spring 컨테이너에 등록
public class MemberService {

        /* 기존에는 회원 서비스가 메모리 회원 리포지토리를 직접 생성하게 함
        private final MemberRepository memberRepository = new MemoryMemberRepository();*/

        //회원 repository 필요
        private final MemberRepository memberRepository;
        @Autowired
        public MemberService(MemberRepository memberRepository) { //DI(dependency injection, 의존성 주입)
                this.memberRepository = memberRepository;
        }

        // 회원가입
        public Long join(Member member){
                //같은 이름이 있는 중복 회원x
                validateDuplicateMember(member); // 중복 회원 검증
                memberRepository.save(member);// 회원가입 저장
                return member.getId();
        }

        private void validateDuplicateMember(Member member) {
                memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                                throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
                  /*
                Optional<Member> result = memberRepository.findByName(member.getName());
                result.ifPresent(m ->{
                        throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
                */
        }

        //전체 회원 조회
        public List<Member> findMembers(){
               return memberRepository.findAll();
        }

        public Optional<Member> findOne(Long memberId){
                return memberRepository.findById(memberId);
        }


}
