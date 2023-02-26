package com.Easy.webcarpool.api.service;

import com.Easy.webcarpool.api.dto.chat.RoomDto;
import com.Easy.webcarpool.api.dto.location.LocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final SimpMessagingTemplate template;

    private Map<String, RoomDto> roomDtoMap;

    @PostConstruct  //의존성 주입이 이루어진후 초기화 수
    private void init(){
        roomDtoMap = new LinkedHashMap<>();
    }

    public void sendLocation(LocationDto locationDto){
        template.convertAndSend("/sub/location/room"+locationDto.getRoomId(), locationDto);
    }
}
