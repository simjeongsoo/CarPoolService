package com.Easy.webcarpool.api.controller;

import com.Easy.webcarpool.api.dto.chat.ChatMessageDto;
import com.Easy.webcarpool.api.dto.chat.ReservedPostDto;
import com.Easy.webcarpool.api.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StompChatController {

    private final ChatService chatService;

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDto newMessage){
        chatService.sendMessage(newMessage);
    }


    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/api/chat/registerReservedPost")
    public ResponseEntity<String> registerReservedPost(@RequestBody ReservedPostDto dto){

        chatService.registerReservedPost(dto);

        return ResponseEntity.ok("success");
    }// registerReservedPost

}
