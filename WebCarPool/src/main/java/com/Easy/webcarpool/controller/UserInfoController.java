package com.Easy.webcarpool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserInfoController {

    @GetMapping("/info")
    public String userInfo() {
        return "info/info";
    }
}
