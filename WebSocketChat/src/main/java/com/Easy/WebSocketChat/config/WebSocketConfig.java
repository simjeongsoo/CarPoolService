package com.Easy.WebSocketChat.config;

import com.Easy.WebSocketChat.handler.ChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


/*
* 핸들러를 이용해 WebSocket을 활성화 하기 위한 Conpig
* @EnableWebSocket 어노테이션을 사용하여 WebSocket을 활성화
* WebSocket에 접속하기 위한 endpoint 는 /chat 으로 설정
* 도메인이 다른 서버에 접속 가능하도록 CORS : setAllowedOrigins("*"); Cnrk
* ws://localhost:8080/chat으로 커넥션을 연결하고 메세지 통신을 할 수 있는 준비
*
* */
@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final ChatHandler chatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
        registry.addHandler(chatHandler, "ws/chat").setAllowedOrigins("*");
    }
}
