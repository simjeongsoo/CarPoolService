package com.spring.SpringCRUD.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


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
        //http://localhost:8080/hello-mvc?name=spring
    }

    /*API*/

    //@ResponseBody 문자반환
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name){

        return "hello "+ name;
        //http://localhost:8080/hello-string?name=testtest
    }

    //@ResponseBody 객체반환
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloapi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);


        return hello;
        //http://localhost:8080/hello-api?name=test
    }

    static class Hello{
        private String name;

        //메서드 접근을 위한 java bean 규약 (property 접근 방식)
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
