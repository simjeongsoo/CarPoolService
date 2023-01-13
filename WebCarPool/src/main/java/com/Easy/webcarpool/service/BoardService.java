package com.Easy.webcarpool.service;

import com.Easy.webcarpool.model.Board;
import com.Easy.webcarpool.model.User;
import com.Easy.webcarpool.repository.BoardRepository;
import com.Easy.webcarpool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public Board save(String username, Board board){
//        User user = userRepository.findByUsername(username);
        User user = userRepository.findByUsername(username).get();
        board.setUser(user);
        return boardRepository.save(board);
    }
}
