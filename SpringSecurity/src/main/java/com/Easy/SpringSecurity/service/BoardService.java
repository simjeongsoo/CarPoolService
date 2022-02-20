package com.Easy.SpringSecurity.service;

import com.Easy.SpringSecurity.model.Board;
import com.Easy.SpringSecurity.model.User;
import com.Easy.SpringSecurity.repository.BoardRepository;
import com.Easy.SpringSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public Board save(String username, Board board){
        User user = userRepository.findByUsername(username);
        board.setUser(user);
        return boardRepository.save(board);
    }
}
