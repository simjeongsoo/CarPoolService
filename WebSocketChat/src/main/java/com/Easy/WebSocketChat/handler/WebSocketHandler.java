package com.Easy.WebSocketChat.handler;


import com.Easy.WebSocketChat.dto.ChatMessage;
import com.Easy.WebSocketChat.dto.ChatRoom;
import com.Easy.WebSocketChat.repository.ChatRoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class WebSocketHandler  extends TextWebSocketHandler {
    //private List<WebSocketSession> sessions = new ArrayList<>();
    private final ChatRoomRepository chatRoomRepository;
    private final ObjectMapper objectMapper;

//
//    /**
//     * 접속 시 실행되는 메소드
//     * - session : 사용자의 웹소켓 정보(HttpSession이 아님)
//     */
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
//        sessions.add(session);
//        log.info("접속 : {}", session);
//    }

    /**
     * 메세지 수신 시 실행될 메소드
     * - session : 사용자(전송한 사용자)의 웹소켓 정보(HttpSession이 아님!)
     * - message : 사용자가 전송한 메세지 정보
     * 		- payload : 실제 보낸 내용
     * 		- byteCount : 보낸 메세지 크기(byte)
     * 		- last : 메세지 종료 여부
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        log.info("메세지 전송 = {} : {}",session,message.getPayload());
        String msg = message.getPayload();
        ChatMessage chatMessage = objectMapper.readValue(msg, ChatMessage.class);
        ChatRoom chatRoom = chatRoomRepository.findRoomById(chatMessage.getChatRoomId());
        chatRoom.handleMessage(session,chatMessage,objectMapper);


        /*
        //payload : 전송되는 데이터
        String payload = message.getPayload();

        log.info("메시지 전송 = {} : {}",session,payload);
        for (WebSocketSession sess : sessions){
            TextMessage msg = new TextMessage(payload);
            sess.sendMessage(msg);
        }

         */
    }

//    /**
//     * 사용자 접속 종료시 실행되는 메소드
//     * - session : 사용자의 웹소켓 정보(HttpSession이 아님!)
//     * - status : 접속이 종료된 원인과 관련된 정보
//     */
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
//        sessions.remove(session);
//        log.info("퇴장 : {}",session);
//    }
}
