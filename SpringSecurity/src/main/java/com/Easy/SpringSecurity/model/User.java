package com.Easy.SpringSecurity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private Boolean enable;

    // Role table과 ManyToMany 매핑
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    private List<Role> roles = new ArrayList<>(); // 사용자 권한 양방향 매핑

    // 양방향 매핑, board 클래스 ManyToOne에서 사용한 변수명 "user"
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY) //쓸데없는 쿼리 안불러오는 LAZY
//    @JsonIgnore
    private List<Board> boards = new ArrayList<>();
}
