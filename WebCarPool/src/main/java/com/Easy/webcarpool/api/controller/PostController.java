package com.Easy.webcarpool.api.controller;

import com.Easy.webcarpool.api.dto.post.*;
import com.Easy.webcarpool.api.dto.user.AndroidLocalUserDto;
import com.Easy.webcarpool.api.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/post")
public class PostController {
    private final PostService postService;

    /*
    * 태워주세요 게시글 저장
    * */
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/passenger/register")
    public ResponseEntity<String> registerPassenger(@RequestBody PostPassengerDto dto){
        String message = postService.savePassengerPost(dto);
        return ResponseEntity.ok(message);
    }

    /*
    * 타세요 게시글 저장
    * */
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/driver/register")
    public ResponseEntity<String> registerDriver(@RequestBody PostDriverDto dto){
        String message = postService.saveDriverPost(dto);
        return ResponseEntity.ok(message);
    }


    /*
    * 태워주세요 게시글 조회
    * */
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/passenger/getPost")
    public List<PostDto> getPassengerPost(@RequestBody int currentPage){
        return postService.getPassengerPost(currentPage);
    }

    /*
    * 타세요 게시글 조회
    * */
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/driver/getPost")
    public List<PostDto> getDriverPost(@RequestBody int currentPage){
        return postService.getDriverPost(currentPage);
    }

    /*
    * 타세요 게시글 조회
    * */
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/user/getPost")
    public List<PostDto> getDriverPost(@RequestBody AndroidLocalUserDto androidLocalUserDto){
        return postService.getUserPost(androidLocalUserDto);
    }

    /*
    * 타세요 게시글 조회
    * */
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/getUserPostData")
    public UserPostDto getUserPostData(@RequestBody AndroidLocalUserDto androidLocalUserDto){
        //--User의 작성 게시글 혹은 진행중 게시글 조회--//
        return postService.getUserPostData(androidLocalUserDto);
    }


    /*
    * 타세요 게시글 조회
    * */
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/getPostByDistrict")
    public List<PostDto> getPostByDistrict(@RequestBody PostDistrictDto postDistrictDto){
        return postService.getPostByDistrict(postDistrictDto);
    }

    /*
    * 진행중 -> 완료처리 (후기 저장)
    * */
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/progressToComplete")
    public ResponseEntity<String> progressToComplete(@RequestBody PostReviewDto dto){
        postService.progressToComplete(dto);
        return ResponseEntity.ok("success");
    }
}
