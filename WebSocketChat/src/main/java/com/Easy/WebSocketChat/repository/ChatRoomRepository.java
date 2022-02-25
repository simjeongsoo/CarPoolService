package com.Easy.WebSocketChat.repository;

import com.Easy.WebSocketChat.dto.ChatRoom;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ChatRoomRepository {
    private Map<String, ChatRoom>chatRoomMap;

    @PostConstruct
    private void init(){
        chatRoomMap = new LinkedHashMap<>();
    }

    //전체 방 목록 출력
    public List<ChatRoom>findAllRoom(){
        List chatRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    // 메시지를 받아 해당 룸번호에만 전송
    public ChatRoom findRoomById(String id){
        return chatRoomMap.get(id);
    }

    public ChatRoom createChatRoom(String nama){
        ChatRoom chatRoom = ChatRoom.create(nama);
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }


}
