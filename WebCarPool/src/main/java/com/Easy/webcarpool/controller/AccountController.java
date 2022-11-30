package com.Easy.webcarpool.controller;

import com.Easy.webcarpool.model.User;
import com.Easy.webcarpool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(){
        return "account/login";
    }

    //--오류 예상--//
    @GetMapping("/register")
    public String register(){
        return "account/register";
    }

    @PostMapping("/register")
    public String register(User user){
        //회원가입시 패스워드 암호화 , 사용자 권한 추가 -> service 클래스 에서 처리
        userService.save(user);
        return "redirect:/";
    }

}
