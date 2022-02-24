package com.Easy.SpringSecurity.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class ChatHandler extends TextWebSocketHandler{
    private static List<WebSocketSession> sessions = new ArrayList<>();

    /**
     * 접속 시 실행되는 메소드
     * - session : 사용자의 웹소켓 정보(HttpSession이 아님)
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        log.info(" 클라이언트 접속 : {}",session);
        super.afterConnectionEstablished(session);
    }

    /**
     * 사용자 접속 종료시 실행되는 메소드
     * - session : 사용자의 웹소켓 정보(HttpSession이 아님!)
     * - status : 접속이 종료된 원인과 관련된 정보
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(" 클라이언트 접속 해제 : {}",session);
        log.info("status : {}",status);
        super.afterConnectionClosed(session, status);
    }

    /**
     * 메세지 수신 시 실행될 메소드
     * - session : 사용자(전송한 사용자)의 웹소켓 정보(HttpSession이 아님!)
     * - message : 사용자가 전송한 메세지 정보
     * 		- payload : 실제 보낸 내용
     * 		- byteCount : 보낸 메세지 크기(byte)
     * 		- last : 메세지 종료 여부
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("메시지 전송 = {} : {}",session,payload);

        for (WebSocketSession sess : sessions){
            sess.sendMessage(message);
        }
        super.handleTextMessage(session, message);
    }
}
