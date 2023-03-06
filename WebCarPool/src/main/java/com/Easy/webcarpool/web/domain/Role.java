package com.Easy.webcarpool.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;// 권한 이름

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore // role을 조회할 때 json에 표시 안함
    private List<User> users;

    @Builder
    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
