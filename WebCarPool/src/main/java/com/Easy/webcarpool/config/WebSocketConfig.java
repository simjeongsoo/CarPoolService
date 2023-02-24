package com.Easy.webcarpool.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final String URL = "http://172.30.1.2:8080";

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub");
        config.setApplicationDestinationPrefixes("/pub");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/sub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint("/mobileServer").setAllowedOrigins(URL).withSockJS();
    }


/*
    private final ChatHandler chatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // ws/chat Endpoint로 Handshake가 이루어
        registry.addHandler(chatHandler,"ws/chat")
                .setAllowedOrigins("*")
                .withSockJS()
                .setClientLibraryUrl("http://localhost:8080/js/sockjs.min.js");
    }//.withSockJS() 추가
    //setAllowedOrigins("*")에서 *라는 와일드 카드를 사용하면
    //보안상의 문제로 전체를 허용하는 것보다 직접 하나씩 지정해주어야 함


 */
}
