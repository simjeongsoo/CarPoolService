package com.spring.SpringCRUD.domain;

//회원 객체(정보)
public class Member {
    private Long id; //데이터 구분을 위해 시스템에서 사용할 id
    private String name; // 회원가입시 사용자가 사용할 이름

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
