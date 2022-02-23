package com.Easy.WebSocketChat.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

//client가 보낸 메시지를 처리하기 위한 handler
@Slf4j
@Component
public class WebSocketHandler  extends TextWebSocketHandler {
    private List<WebSocketSession> sessions = new ArrayList<>();

    //연결
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        sessions.add(session);
        log.info("접속 : {}", session);
    }

    //메세지 전송
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        //payload : 전송되는 데이터
        log.info("메시지 전송 = {} : {}",session,message.getPayload());
        for (WebSocketSession sess : sessions){
            TextMessage msg = new TextMessage(message.getPayload());
            sess.sendMessage(msg);
        }
    }

    //연결 끊어짐
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
        sessions.remove(session);
        log.info("퇴장 : {}",session);
    }
}
