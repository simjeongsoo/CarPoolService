package com.Easy.webcarpool.api.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class AccuseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accuse_type;

    @Column(nullable = false)
    private String accused_nickname;

    @Column(nullable = false)
    private String accuser_nickname;

    @Column(nullable = false)
    private String accuse_content;
}
