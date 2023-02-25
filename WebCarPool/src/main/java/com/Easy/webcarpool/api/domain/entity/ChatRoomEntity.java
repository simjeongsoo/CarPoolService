package com.Easy.webcarpool.api.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ChatRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String roomid;

    @Column(nullable = false)
    private String postType;

    @Column(nullable = false)   //어떤 게시글에 대한 채팅방인지 식별
    private Long postId;

    @Column(nullable = false)
    private String driver;

    @Column(nullable = false)
    private String passenger;

    @Column(nullable = false)
    private String passengerNickname;

    @Column(nullable = false)
    private String driverNickname;

    @Column(nullable = false)
    private String driverFcmToken;

    @Column(nullable = false)
    private String passengerFcmToken;

    @Column(nullable = false)
    private String lastChatTime;

    @Column(nullable = true)
    private String lastMessage;
}
