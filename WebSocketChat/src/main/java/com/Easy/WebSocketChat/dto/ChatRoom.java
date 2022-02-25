package com.Easy.WebSocketChat.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ChatRoom {
    private String roomId; // 채팅방 번호
    private String name; // 채팅방 이름
    private Set<WebSocketSession>sessions = new HashSet<>(); // 채팅방에 접속해 있는 세션 관리

    public static ChatRoom create(String name){
        ChatRoom chatRoom = new ChatRoom();

        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }

    //Message를 받아서 메시지 타입이 입장이나 퇴장이면 그에 맞게 메세지를 수정하고 해당 방의 세션들에 메세지 전
    public void handleMessage(WebSocketSession session, ChatMessage chatMessage
            , ObjectMapper objectMapper)throws IOException {

        if (chatMessage.getType() == ChatMessage.MessageType.ENTER){
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getWriter() + " 님이 입장하셨습니다.");
        }
        else if (chatMessage.getType() == ChatMessage.MessageType.LEAVE){
            sessions.remove(session);
            chatMessage.setMessage(chatMessage.getWriter() +" 님이 퇴장하셨습니다.");
        }
        else {
            chatMessage.setMessage(chatMessage.getWriter()+" : "+chatMessage.getMessage());
        }
        send(chatMessage, objectMapper);
    }

    private void send(ChatMessage chatMessage,ObjectMapper objectMapper)throws IOException{
        TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(chatMessage.getMessage()));
        for (WebSocketSession sess : sessions){
            sess.sendMessage(textMessage);
        }
    }



}
