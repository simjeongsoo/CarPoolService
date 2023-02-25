package com.Easy.webcarpool.api.dto.calender;

import com.Easy.webcarpool.api.domain.entity.PostDriver;
import com.Easy.webcarpool.api.domain.entity.PostPassenger;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarPostDto {


    private String type;
    private String email;
    private String nickname;
    private String gender;
    private String departure;
    private String destination;
    private String departureDate;
    private String departureTime;
    private String gift;
    private String message;
    private String fcmToken;



    public CalendarPostDto(PostPassenger entity){
        this.type = entity.getType();
        this.email = entity.getEmail();
        this.nickname = entity.getNickname();
        this.gender = entity.getGender();
        this.departure = entity.getDeparture();
        this.destination = entity.getDestination();
        this.departureDate = entity.getDepartureDate();
        this.departureTime = entity.getDepartureTime();
        this.gift = entity.getGift();
        this.message = entity.getMessage();
        this.fcmToken = entity.getFcmToken();
    }

    public CalendarPostDto(PostDriver entity) {
        this.type = entity.getType();
        this.email = entity.getEmail();
        this.nickname = entity.getNickname();
        this.gender = entity.getGender();
        this.departure = entity.getDeparture();
        this.destination = entity.getDestination();
        this.departureDate = entity.getDepartureDate();
        this.departureTime = entity.getDepartureTime();
        this.gift = entity.getGift();
        this.message = entity.getMessage();
        this.fcmToken = entity.getFcmToken();
    }

}
