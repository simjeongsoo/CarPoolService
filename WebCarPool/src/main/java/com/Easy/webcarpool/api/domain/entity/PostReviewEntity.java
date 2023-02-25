package com.Easy.webcarpool.api.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class PostReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String host_email;

    @Column(nullable = false)
    private String host_nickname;

    @Column(nullable = false)
    private String writer_email;

    @Column(nullable = false)
    private String writer_nickname;

    @Column(nullable = false)
    private Float rate;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;
}
