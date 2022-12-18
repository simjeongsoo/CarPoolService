package com.Easy.webcarpool.model;

import com.Easy.webcarpool.dto.ProfileResponseDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // pk

    private String realname; // 실명
    private String username; // 닉네임(아이디)
    private String email;    // 이메일
    private String password; // 비밀번호
    private Boolean enable;  // 활성화 여부
    private String address;  // 주소
    private boolean gender;  // 성별
    @Size(max = 8)
    private String birth;    // 생년월일
    private String introduce; // 자기소개

//    private String introduce; // 자기소개


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
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY) //쓸데없는 쿼리 안불러오는 LAZY
//    @JsonIgnore
    private List<Board> boards = new ArrayList<>();

    @Builder
    public User(String realname, String username, String email, String address, boolean gender, String birth, String introduce) {
        this.realname = realname;
        this.username = username;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.birth = birth;
//        this.introduce = introduce;
    }
}
