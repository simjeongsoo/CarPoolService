package com.Easy.webcarpool.api.domain.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomid;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String time;

}
