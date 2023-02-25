package com.Easy.webcarpool.api.dto.chat;

import com.Easy.webcarpool.api.domain.entity.ChatMessageEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class ChatMessageDto {
    private String roomId;
    private String type;
    private String writer;
    private String message;
    private String fcmToken;
    private String time;

    public void setCurrentTime(){
        LocalTime now = LocalTime.now();
        System.out.println(now);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH시 mm분");
        String formatedNow = now.format(formatter);
        this.time = formatedNow;
    }

    public ChatMessageEntity toEntity(){

        return ChatMessageEntity.builder()
                .roomid(roomId)
                .type(type)
                .writer(writer)
                .message(message)
                .time(time)
                .build();
    }

    @Builder
    public ChatMessageDto(String roomId, String writer, String message, String time){
        this.roomId = roomId;
        this.writer = writer;
        this.message = message;
        this.time = time;
    }

    public ChatMessageDto(ChatMessageEntity entity){
        this.roomId = entity.getRoomid();
        this.type = entity.getType();
        this.writer = entity.getWriter();
        this.message = entity.getMessage();
        this.time = entity.getTime();
    }
}
