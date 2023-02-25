package com.Easy.webcarpool.api.dto.post;

import com.Easy.webcarpool.api.domain.entity.PostDriver;
import com.Easy.webcarpool.api.domain.entity.PostPassenger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PostDto {

    private Long postId;
    private String type;
    private String email;
    private String nickname;
    private String gender;
    private Float rate;
    private String departure;
    private String destination;
    private String departureDate;
    private String departureTime;
    private String gift;
    private String message;
    private String fcmToken;

    public PostDto(PostPassenger entity){
        this.postId = entity.getId();
        this.type = entity.getType();
        this.email = entity.getEmail();
        this.nickname = entity.getNickname();
        this.gender = entity.getGender();
        this.rate = entity.getRate();
        this.departure = entity.getDeparture();
        this.destination = entity.getDestination();
        this.departureDate = entity.getDepartureDate();
        this.departureTime = entity.getDepartureTime();
        this.gift = entity.getGift();
        this.message = entity.getMessage();
        this.fcmToken = entity.getFcmToken();
    }

    public PostDto(PostDriver entity) {
        this.postId = entity.getId();
        this.type = entity.getType();
        this.email = entity.getEmail();
        this.nickname = entity.getNickname();
        this.gender = entity.getGender();
        this.rate = entity.getRate();
        this.departure = entity.getDeparture();
        this.destination = entity.getDestination();
        this.departureDate = entity.getDepartureDate();
        this.departureTime = entity.getDepartureTime();
        this.gift = entity.getGift();
        this.message = entity.getMessage();
        this.fcmToken = entity.getFcmToken();
    }


}
