package com.Easy.webcarpool.api.service;

import com.Easy.webcarpool.api.domain.entity.PostDriver;
import com.Easy.webcarpool.api.domain.entity.PostPassenger;
import com.Easy.webcarpool.api.dto.post.PostDto;
import com.Easy.webcarpool.api.dto.chat.ReservedPostDto;
import com.Easy.webcarpool.api.dto.user.AndroidLocalUserDto;
import com.Easy.webcarpool.api.repository.PostDriverRepository;
import com.Easy.webcarpool.api.repository.PostPassengerRepository;
import com.Easy.webcarpool.api.repository.ReservedPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarService {
    private final PostPassengerRepository postPassengerRepository;
    private final PostDriverRepository postDriverRepository;
    private final ReservedPostRepository reservedPostRepository;

    /*
    * 타세요 게시글 조회 로직
    * */
    @Transactional
    public List<ReservedPostDto> getCalendarPostData(AndroidLocalUserDto androidLocalUserDto) {

        List<ReservedPostDto> reservedPostDtos = reservedPostRepository.findReservedPostByEmail(androidLocalUserDto.getEmail()).stream()
                .map(ReservedPostDto::new)
                .collect(Collectors.toList());

        return reservedPostDtos;
    }

    /*
    * 캘린더 조회 로직
    * */
    public PostDto getPostDtoById(ReservedPostDto reservedPostDto) {
        if(reservedPostDto.getPostType().equals("passenger")){ //태워주세요 테이블 조회

            PostPassenger postPassenger = postPassengerRepository.findById(reservedPostDto.getPostId())
                    .orElseThrow(()->
                            new IllegalArgumentException("해당 게시글이 없습니다."));

            return new PostDto(postPassenger);

        }else { // 타세요 테이블 조회

            PostDriver postDriver = postDriverRepository.findById(reservedPostDto.getPostId())
                    .orElseThrow(()->
                            new IllegalArgumentException("해당 게시글이 없습니다."));

            return new PostDto(postDriver);
        }
    }

}
