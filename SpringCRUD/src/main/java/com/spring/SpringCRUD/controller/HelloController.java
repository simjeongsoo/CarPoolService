package com.spring.SpringCRUD.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/*
* 컨트롤러에서 리턴 값으로 문자를 반환하면 뷰 리졸버( viewResolver )가 화면을 찾아서 처리
* */
@Controller
public class HelloController {

    //thymeleaf 템플릿 엔진 동작 확인
    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data","Hello Spring");

        return "hello";
    }

    //MVC와 템플릿 엔진
    //@RequestParm 을 이용해 data를 받아 model 에 담아 view로 이동
    @GetMapping("hello-mvc")
    public String helloMVC(@RequestParam("name") String name,Model model){
        model.addAttribute("name",name);
        return "hello-templete";
    }
}
