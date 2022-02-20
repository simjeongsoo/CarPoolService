package com.Easy.SpringSecurity.controller;

import com.Easy.SpringSecurity.model.Board;
import com.Easy.SpringSecurity.model.User;
import com.Easy.SpringSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

//JPA이용해서 DB의 데이터 조작할 수 있는 컨트롤러 생성
    @RestController
    @RequestMapping("/api")
    class UserApiController {

    @Autowired
    private UserRepository repository;

    @GetMapping("/users") //게시글검색
    List<User> all() {
       return repository.findAll();
    }

        @PostMapping("/users")
        User newUser (@RequestBody User newUser){
            return repository.save(newUser);
        }

        // Single item

        @GetMapping("/users/{id}")
        User one (@PathVariable Long id){

            return repository.findById(id).orElse(null);
        }

        @PutMapping("/users/{id}")
        User replaceUser (@RequestBody User newUser, @PathVariable Long id){

            return repository.findById(id)
                    .map(user -> {
//                        user.setTitle(newUser.getTitle());
//                        user.setContent(newUser.getContent());
//                        user.setBoards(newUser.getBoards());
                        user.getBoards().clear();
                        user.getBoards().addAll(newUser.getBoards());
                        //사용자 정보를 담음
                        for (Board board : user.getBoards()){
                            board.setUser(user);
                        }

                        return repository.save(user);
                    })
                    .orElseGet(() -> {
                        newUser.setId(id);
                        return repository.save(newUser);
                    });
        }

        @DeleteMapping("/users/{id}")
        void deleteUser (@PathVariable Long id){
            repository.deleteById(id);
        }
    }

