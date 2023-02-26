package com.Easy.webcarpool.api.service;

import com.Easy.webcarpool.api.domain.entity.PostDriver;
import com.Easy.webcarpool.api.domain.entity.PostPassenger;
import com.Easy.webcarpool.api.domain.entity.ReservedPostEntity;
import com.Easy.webcarpool.api.domain.entity.User;
import com.Easy.webcarpool.api.dto.chat.RoomDto;
import com.Easy.webcarpool.api.dto.post.*;
import com.Easy.webcarpool.api.dto.user.AndroidLocalUserDto;
import com.Easy.webcarpool.api.repository.PostDriverRepository;
import com.Easy.webcarpool.api.repository.PostPassengerRepository;
import com.Easy.webcarpool.api.repository.PostReviewRepository;
import com.Easy.webcarpool.api.repository.ReservedPostRepository;
import com.Easy.webcarpool.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostPassengerRepository postPassengerRepository;
    private final PostDriverRepository postDriverRepository;
    private final PostReviewRepository postReviewRepository;
    private final UserRepository userRepository;
    private final ReservedPostRepository reservedPostRepository;

    /*
    * 태워주세요 게시글 등록 ,저장
    * */
    @Transactional
    public String savePassengerPost(PostPassengerDto dto){
        postPassengerRepository.save(dto.toEntity());
        return "success";
    }

    /*
     * 타세요 게시글 등록, 저장
     * */
    @Transactional
    public String saveDriverPost(PostDriverDto dto){
        postDriverRepository.save(dto.toEntity());
        return "success";
    }

    /*
     * 태워주세요 게시글 조회
     * */
    @Transactional
    public List<PostDto> getPassengerPost(int currentPage){
        Pageable limit = PageRequest.of(currentPage,10);
        return postPassengerRepository.findAll(limit).stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

    /*
     * 타세요 게시글 조회
     * */
    @Transactional
    public List<PostDto> getDriverPost(int currentPage){
        Pageable limit = PageRequest.of(currentPage,10);
        return postDriverRepository.findAll(limit).stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

    /*
     * 엑세스한 유저가 작성한 게시글 조회
     * */
    @Transactional
    public List<PostDto> getUserPost(AndroidLocalUserDto androidLocalUserDto){
        //--check --> limitable 적용여부 판단--//
        List<PostDto> postPassenger = postPassengerRepository.findPassengerPostByEmail(androidLocalUserDto.getEmail()).stream()
                .map(PostDto::new)
                .collect(Collectors.toList());

        List<PostDto> postDriver = postDriverRepository.findDriverPostByEmail(androidLocalUserDto.getEmail()).stream()
                .map(PostDto::new)
                .collect(Collectors.toList());

        List<PostDto> posts = new ArrayList<>();
        posts.addAll(postDriver);
        posts.addAll(postPassenger);

        return posts;
    }

    @Transactional
    public UserPostDto getUserPostData(AndroidLocalUserDto androidLocalUserDto) {

        List<PostPassenger> postPassengers = postPassengerRepository.findPassengerPostByEmail(androidLocalUserDto.getEmail());
        List<PostDriver> postDrivers = postDriverRepository.findDriverPostByEmail(androidLocalUserDto.getEmail());

        return UserPostDto.builder()
                .passenger(Integer.toString(postPassengers.size()))
                .driver(Integer.toString(postDrivers.size()))
                .ongoing("0")
                .build();
        // 진행중 0 -> reservedPostDto 개수 포함히여 리턴하도록 변경
    }

    /*
     * 요청 게시글 로직
     * */
    @Transactional
    public PostDto findPostById(RoomDto roomDto){
        if(roomDto.getPostType().equals("passenger")){  //요청 게시글이 태워주세요
            PostPassenger postPassenger = postPassengerRepository.findById(roomDto.getPostId())
                    .orElseThrow(()->
                            new IllegalArgumentException("해당 게시글이 없습니다."));

            return new PostDto(postPassenger);
        }else{  //요청 게시글이 타세요
            PostDriver postDriver = postDriverRepository.findById(roomDto.getPostId())
                    .orElseThrow(()->
                            new IllegalArgumentException("해당 게시글이 없습니다."));

            return new PostDto(postDriver);
        }

    }


    /*
     * 유저가 검색한 지역명 기반 게시글 조회
     * */
    public List<PostDto> getPostByDistrict(PostDistrictDto postDistrictDto) {

        if(postDistrictDto.getPostType().equals("driver")){ // 타세요
            return postDriverRepository.findDriverPostByDistrict(postDistrictDto.getDistrict()).stream()
                    .map(PostDto::new)
                    .collect(Collectors.toList());
        }else if (postDistrictDto.getPostType().equals("passenger")){    //태워주세요
            return postPassengerRepository.findPassengerPostByDistrict(postDistrictDto.getDistrict()).stream()
                    .map(PostDto::new)
                    .collect(Collectors.toList());
        }else{  //user
            return null;
        }
    }

    @Transactional
    public void progressToComplete(PostReviewDto dto){

        User user = userRepository.findUserByEmail(dto.getHost_email()).get();
        if(user != null){
            Float rate = (user.getRate()+dto.getRate())/2;
            userRepository.updateRate(dto.getHost_email(), rate);
            Optional<ReservedPostEntity> reservedPostEntity = reservedPostRepository.findReservedPostByIdAndType(dto.getPostId(), dto.getPostType());
            if(reservedPostEntity.isPresent()){
                reservedPostRepository.delete(reservedPostEntity.get());
            }   //PostReviewDto의 postId와 PostType에 해당하는 예약건이 있을경우 삭제 trigger에 의해 complete테이블로 이동
        }
        postReviewRepository.save(dto.toEntity());
    }

}
