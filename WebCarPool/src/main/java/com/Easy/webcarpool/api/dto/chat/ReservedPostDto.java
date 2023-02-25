package com.Easy.webcarpool.api.dto.chat;

import com.Easy.webcarpool.api.domain.entity.ReservedPostEntity;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ReservedPostDto {

    private Long postId;
    private String postType;
    private String locationRoomId;
    private String driver;
    private String driverFcmToken;
    private String passenger;
    private String passengerFcmToken;
    private String date;
    private String time;

    public ReservedPostEntity toEntity(){
        return ReservedPostEntity.builder()
                .postId(postId)
                .postType(postType)
                .locationRoomId(UUID.randomUUID().toString())
                .driver(driver)
                .driverFcmToken(driverFcmToken)
                .passenger(passenger)
                .passengerFcmToken(passengerFcmToken)
                .date(date)
                .time(time)
                .build();
    }//toEntity

    public ReservedPostDto(ReservedPostEntity entity){
        this.postId = entity.getPostId();
        this.postType = entity.getPostType();
        this.locationRoomId = entity.getLocationRoomId();
        this.driver = entity.getDriver();
        this.driverFcmToken = entity.getDriverFcmToken();
        this.passenger = entity.getPassenger();
        this.passengerFcmToken = entity.getPassengerFcmToken();
        this.date = entity.getDate();
        this.time = entity.getTime();
    } // constructor

}
