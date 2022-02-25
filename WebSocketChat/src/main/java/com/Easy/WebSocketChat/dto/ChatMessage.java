package com.Easy.WebSocketChat.dto;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;


@Getter
@Setter
public class ChatMessage {

    private String ChatRoomId; // 방번호
    private String writer; // 보낸사람
    private String message; //메세지
    private MessageType type; // 메세지 타입


    public enum MessageType{// 입장시, 채팅시 , 떠날때
        ENTER,CHAT,LEAVE
    }
}
