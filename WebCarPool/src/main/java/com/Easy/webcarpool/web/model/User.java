package com.Easy.webcarpool.web.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
//@Data
@Getter @Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // pk

    private String realname;    // 실명
    private String username;    // 닉네임(아이디)
    private String email;       // 이메일
    private String password;    // 비밀번호
    private Boolean enable;     // 활성화 여부
    private String address;     // 주소
    private boolean gender;     // 성별
//    @Size(max = 8)
    private String birth;       // 생년월일
    private String introduce;   // 자기소개

    private String profileImgOrgNm;         // 프로필 이미지 업로드 이름
    private String profileImgSavedNm;       // 프로필 이미지 저장 이름
    private String profileImgSavedPath;     // 프로필 이미지 경로

    @Builder
    public User(String realname, String username, String email, String address, boolean gender, String birth, String introduce, String profileImgSavedPath, String profileImgOrgNm, String profileImgSavedNm) {
        this.realname = realname;
        this.username = username;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.birth = birth;
        this.introduce = introduce;
        this.profileImgSavedPath = profileImgSavedPath;
        this.profileImgOrgNm = profileImgOrgNm;
        this.profileImgSavedNm = profileImgSavedNm;
    }

    /**
     * 연관관계 매핑
     * */

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private UserCar userCar; // 유저차량정보와 1:1 양방향 매핑



    /**
     * 정보수정
     * dirty checking
     * */
    // 프로필 업데이트
    public void updateProfile(String address, String birth, String introduce) {
        this.address = address;
        this.birth = birth;
        this.introduce = introduce;
    }

    // 프로필 이미지 업데이트
    public void updateProfileImg(String profileImgOrgNm, String profileImgSavedNm, String profileImgSavedPath) {
        this.profileImgOrgNm = profileImgOrgNm;
        this.profileImgSavedNm = profileImgSavedNm;
        this.profileImgSavedPath = profileImgSavedPath;
    }

    public void updateUserCar(String username, UserCar userCar) { // 테스트용 메서드(delete)
        this.username = username;
        this.userCar = userCar;
    }
}
