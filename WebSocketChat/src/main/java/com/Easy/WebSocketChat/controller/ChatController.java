package com.Easy.WebSocketChat.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


// 라우터 잡음
@Controller
@Log4j2
public class ChatController {

    @GetMapping("/chat")
    public String chatGET(){
        log.info("@ChatController, chat CET()");

        return "chat";
    }
}
