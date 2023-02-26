package com.Easy.webcarpool.api.service;

import com.Easy.webcarpool.api.domain.entity.ChatRoomEntity;
import com.Easy.webcarpool.api.dto.chat.ChatMessageDto;
import com.Easy.webcarpool.api.dto.chat.RoomDto;
import com.Easy.webcarpool.api.dto.chat.ReservedPostDto;
import com.Easy.webcarpool.api.repository.ChatMessageRepository;
import com.Easy.webcarpool.api.repository.ChatRoomRepository;
import com.Easy.webcarpool.api.repository.ReservedPostRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ChatService {

    private Map<String, RoomDto> roomDtoMap;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ReservedPostRepository reservedPostRepository;
    private final SimpMessagingTemplate template;
    private final FCMService fcmService;

    @PostConstruct  //의존성 주입이 이루어진후 초기화 수
    private void init(){
        roomDtoMap = new LinkedHashMap<>();
    }

    public String createRoomDto(RoomDto roomDto){
        //--check : create수행시 방이 이미 존재할 경우 기존방을 찾아서 return--//

        Optional<ChatRoomEntity> chatRoomEntity = chatRoomRepository.findChatRoomByDto(roomDto.getPostType(), roomDto.getPostId(), roomDto.getDriver(), roomDto.getPassenger());

        if(chatRoomEntity.isPresent()){ //roomDto와 일치하는 방이 존재할경우 기존의 방 정보를 리턴
            return chatRoomEntity.get().getRoomid();

        }else{  //roomDto와 일치하는 방이 존재하지 않을 경우 새로 save
            roomDto.setCurrentTime();   //방생성시 현재 시간 지정
            return chatRoomRepository.save(roomDto.toEntity()).getRoomid();
        }

    }

    public List<RoomDto> findAllRooms(String userEmail){
        return chatRoomRepository.findChatRoomByEmail(userEmail).stream()
                .map(RoomDto::new)
                .collect(Collectors.toList());
    }

    public void sendMessage(ChatMessageDto newMessage){
        newMessage.setCurrentTime();
        chatMessageRepository.save(newMessage.toEntity()); //채팅 메시지 저장
        template.convertAndSend("/sub/chat/room"+newMessage.getRoomId(), newMessage);
        chatRoomRepository.updateLastChat(newMessage.getRoomId() ,newMessage.getTime(), newMessage.getMessage());
        try{
            fcmService.sendMessageTo(newMessage.getFcmToken(), newMessage.getWriter(), newMessage.getMessage());
        }catch(Exception e){
            System.out.println(e.toString());
        }

        //firebase code
    }

    public List<ChatMessageDto> findMessageByRoomId(String roomId){

        try{
            return chatMessageRepository.findMessageByRoomId(roomId).stream()
                    .map(ChatMessageDto::new)
                    .collect(Collectors.toList());
        }catch (Exception e){
            System.out.println(e);
            return null;
        }

    }

    /*
    * 성사된 예약에 대한 정보 저장
    * */
    public void registerReservedPost(ReservedPostDto dto){
        reservedPostRepository.save(dto.toEntity());
    }

    public void leaveChatRoom(RoomDto roomDto) {
        Optional<ChatRoomEntity> chatRoomEntity = chatRoomRepository.findChatRoomByRoomId(roomDto.getRoomId());
        if(chatRoomEntity.isPresent()){ //존재할 경우
            chatRoomRepository.delete(chatRoomEntity.get());
            chatMessageRepository.deleteMessageByRoomID(roomDto.getRoomId());
        }
        //--leaveChatRoom() -> 채팅방 뿐만아니라 채팅방에 해당하는 메시지 또한 삭제하도록 변경--//
    }
}