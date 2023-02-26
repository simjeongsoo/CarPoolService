package com.Easy.webcarpool.web.controller;

import com.Easy.webcarpool.web.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
@Log4j2
public class ChatController {
    @GetMapping("/chat")
    public String chat(){
        log.info("@ChatController, chat()");
        return "chat/chat";
    }

    private final SimpMessageSendingOperations messagingTemplate;

    public SimpMessageSendingOperations getMessagingTemplate() {
        return messagingTemplate;
    }

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if (ChatMessage.MessageType.ENTER.equals(message.getType()))
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

}
