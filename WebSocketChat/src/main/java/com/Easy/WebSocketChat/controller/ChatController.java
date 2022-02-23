package com.Easy.WebSocketChat.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Log4j2
public class ChatController {

    @GetMapping("/")
    public String chat(){
        log.info("@ChatController, chat in");

        return "chat";
    }
    @GetMapping("/chatfront")
    public String chatFront(){

        return "chatfront";
    }
}
