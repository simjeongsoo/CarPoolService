package com.spring.SpringCRUD.controller;

import com.spring.SpringCRUD.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

//@Controller =  spring 컨테이너가 객체를 생성해서 넣어두고 spring 이 관리(spring bean 이 관리됨)
@Controller
public class MemberController {

    private final MemberService memberService;

    //spring 컨테이너에 등록
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
