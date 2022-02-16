package com.Easy.SpringSecurity.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//mysql board table 연동을 위한 model class
@Entity
@Data // lombok 어노테이션으로 Board 클래스 멤버변수들을 외부에서 꺼내서 쓸 수 있음
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min=2, max=30,message = "제목은 2자이상 30자 이하입니다.")
    private String title;
    private String content;
}
