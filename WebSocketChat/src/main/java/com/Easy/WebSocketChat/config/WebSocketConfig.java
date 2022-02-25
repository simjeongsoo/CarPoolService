package com.Easy.WebSocketChat.config;

import com.Easy.WebSocketChat.handler.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

// WebSocketHandler를 사용하기 위한 설정
// registry 에 등록
@Configuration
@EnableWebSocket //웹소켓 활성화
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
        registry.addHandler(webSocketHandler, "/chat").setAllowedOrigins("*");
    }
}
