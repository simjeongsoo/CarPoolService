package com.Easy.SpringSecurity.controller;

import com.Easy.SpringSecurity.model.Board;
import com.Easy.SpringSecurity.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

//JPA이용해서 DB의 데이터 조작할 수 있는 컨트롤러 생성
    @RestController
    @RequestMapping("/api")
    class BoardApiController {

    @Autowired
    private BoardRepository repository;

    @GetMapping("/boards")
    List<Board> all(@RequestParam(required = false, defaultValue = "") String title,
                    @RequestParam(required = false, defaultValue = "") String content) {
        if (StringUtils.isEmpty(title) && StringUtils.isEmpty(content)) {
            return repository.findAll(); // 전체 데이터 조회
        } else {
            return repository.findByTitleOrContent(title, content);
        }
    }

//
//            if (StringUtils.isEmpty(title)){ // 파라미터가 전달되지 않았다면
//                return repository.findAll(); // 전체 데이터 조회
//            }else {// 전달 되었다면
//                return repository.findByTitle(title); //title로 조회
//            }

        @PostMapping("/boards")
        Board newBoard (@RequestBody Board newBoard){
            return repository.save(newBoard);
        }

        // Single item

        @GetMapping("/boards/{id}")
        Board one (@PathVariable Long id){

            return repository.findById(id).orElse(null);
        }

        @PutMapping("/boards/{id}")
        Board replaceBoard (@RequestBody Board newBoard, @PathVariable Long id){

            return repository.findById(id)
                    .map(board -> {
                        board.setTitle(newBoard.getTitle());
                        board.setContent(newBoard.getContent());
                        return repository.save(board);
                    })
                    .orElseGet(() -> {
                        newBoard.setId(id);
                        return repository.save(newBoard);
                    });
        }

        @DeleteMapping("/boards/{id}")
        void deleteBoard (@PathVariable Long id){
            repository.deleteById(id);
        }
    }

