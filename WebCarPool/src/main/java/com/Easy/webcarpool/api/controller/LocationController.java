package com.Easy.webcarpool.api.controller;

import com.Easy.webcarpool.api.dto.location.LocationDto;
import com.Easy.webcarpool.api.dto.chat.ReservedPostDto;
import com.Easy.webcarpool.api.dto.user.AndroidLocalUserDto;
import com.Easy.webcarpool.api.service.CalendarService;
import com.Easy.webcarpool.api.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LocationController {

    private final CalendarService calendarService;
    private final LocationService locationService;


    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/api/location/getReservedPost")
    public List<ReservedPostDto> getUserPostData(@RequestBody AndroidLocalUserDto androidLocalUserDto) {
        return calendarService.getCalendarPostData(androidLocalUserDto);
    }

    /*
    * 위치 조회 api
    * */
    @MessageMapping(value = "/location/stomp")
    public void sendLocation(LocationDto locationDto){
        locationService.sendLocation(locationDto);
    }
}
