package com.Easy.webcarpool.api.controller;

import com.Easy.webcarpool.api.dto.chat.ChatMessageDto;
import com.Easy.webcarpool.api.dto.chat.RoomDto;
import com.Easy.webcarpool.api.dto.post.PostDto;
import com.Easy.webcarpool.api.dto.user.AndroidLocalUserDto;
import com.Easy.webcarpool.api.service.ChatService;
import com.Easy.webcarpool.api.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/chat")
public class StompRoomController {


    private final ChatService chatService;
    private final PostService postService;

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/createroom")
    public RoomDto createRoom(@RequestBody RoomDto roomDto){

        System.out.println(roomDto.getPostType());

        roomDto.setRoomId(chatService.createRoomDto(roomDto));

        return roomDto;
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/getAllRoom")
    public List<RoomDto> getAllRoom(@RequestBody AndroidLocalUserDto userDto){

        return chatService.findAllRooms(userDto.getEmail());
    }
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/findMessageByRoomId")
    public List<ChatMessageDto> enterRoom(@RequestBody RoomDto roomDto){
        return chatService.findMessageByRoomId(roomDto.getRoomId());
    }


    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/findPostInfo")
    public PostDto findPostInfo(@RequestBody RoomDto roomDto){
        return postService.findPostById(roomDto);
    }


    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/leaveChatRoom")
    public ResponseEntity<String> leaveChatRoom(@RequestBody RoomDto roomDto){
        chatService.leaveChatRoom(roomDto);
        return ResponseEntity.ok("success");
    }   //return succes -> transaction동작 여부에 따라 리턴하도록 수정!

}
