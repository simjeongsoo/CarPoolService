package com.Easy.WebSocketChat.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;


/*
* 다수의 클라이언트가 보낸 메시지를 처리할 핸들러
* 텍스트 기반의 채팅 구현을 위한 TextWebSocketHandler 상속
* client로 부터 받은 메시지를 log 출력
*
* */

@Component
@Log4j2
public class ChatHandler extends TextWebSocketHandler {

    private static List<WebSocketSession> list = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        // 페이로드는 전송되는 데이터
        String payload = message.getPayload();
        log.info("payload : " + payload);

        for (WebSocketSession sess: list){
            sess.sendMessage(message);
        }
    }

    //Client가 접속 시 호출되는 메서드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{

        list.add(session);
        log.info(session + " 클라이언트 접속 ");

    }

    //Client가 접속 시 호출되는 메서드
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
        log.info(session + " 클라이언트 접속 해제");
        list.remove(session);
    }

}














