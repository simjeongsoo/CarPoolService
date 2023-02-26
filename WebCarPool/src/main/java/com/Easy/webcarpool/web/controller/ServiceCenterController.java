package com.Easy.webcarpool.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServiceCenterController {

    @GetMapping("/service-center")
    public String serviceCenter() {
        return "/service-center/service-center";
    }
}
