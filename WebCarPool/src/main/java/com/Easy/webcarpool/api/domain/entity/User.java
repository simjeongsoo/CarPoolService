package com.Easy.webcarpool.api.domain.entity;

import com.Easy.webcarpool.api.domain.user.Role;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String birth;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private float rate;

    @Column(nullable = false)
    private Boolean driverAuthentication;

    @Column(nullable = false)
    private String fcmToken;

    @Column(name = "activated")
    private boolean activated;

    //Enumerated -> JPA로 데이터베이스 생성시 Enum값을 어떤 형태로 저장할지 결정(default : int)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
