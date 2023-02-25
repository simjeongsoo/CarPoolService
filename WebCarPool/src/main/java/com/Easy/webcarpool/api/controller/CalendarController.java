package com.Easy.webcarpool.api.controller;

import com.Easy.webcarpool.api.dto.post.PostDto;
import com.Easy.webcarpool.api.dto.chat.ReservedPostDto;
import com.Easy.webcarpool.api.dto.user.AndroidLocalUserDto;
import com.Easy.webcarpool.api.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calendar")
public class CalendarController {
    private final CalendarService calendarService;

    /*
    * 타세요 게시글 조회 api
    * */
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/post")
    public List<ReservedPostDto> getUserPostData(@RequestBody AndroidLocalUserDto androidLocalUserDto){
        //--사용자, 작성하거나 진행중인 post 정보 조회--//
        return calendarService.getCalendarPostData(androidLocalUserDto);
    }

    /*
    * postDetail 조회 api
    * */
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/detail")
    public PostDto getUserPostData(@RequestBody ReservedPostDto reservedPostDto){

        return calendarService.getPostDtoById(reservedPostDto);
    }
}
