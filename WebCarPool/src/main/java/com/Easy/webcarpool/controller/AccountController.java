package com.Easy.webcarpool.controller;

import com.Easy.webcarpool.dto.SignUpRequestForm;
import com.Easy.webcarpool.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 로그인 컨트롤러
     * */
    @GetMapping("/login")
    public String login(){
        return "account/login";
    }

    /**
     *  회원가입 선택 컨트롤러
     * */
    @GetMapping("/register")
    public String register(){
        return "account/register";
    }

    /**
     * 회원가입 필수입력사항 폼 생성 컨트롤러
     */
    @GetMapping("/email_form")
    public String email(Model model) {
        model.addAttribute("emailForm", new SignUpRequestForm());
        return "account/signup/email_form";
    }

    @PostMapping("/email_form")
    public String register(@Valid SignUpRequestForm form, BindingResult result){
        //회원가입시 패스워드 암호화 , 사용자 권한 추가 -> service 클래스 에서 처리

        if (result.hasErrors()) {
            return "account/signup/email_form";
        }

        userService.join(form);
        return "redirect:/";
    }

}
