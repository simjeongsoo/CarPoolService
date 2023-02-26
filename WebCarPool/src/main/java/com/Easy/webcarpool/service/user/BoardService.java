package com.Easy.webcarpool.service.user;

import com.Easy.webcarpool.model.Board;
import com.Easy.webcarpool.model.User;
import com.Easy.webcarpool.repository.BoardRepository;
import com.Easy.webcarpool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public Board save(String username, Board board){
//        User user = userRepository.findByUsername(username);
        User user = userRepository.findByUsername(username).get();
        board.setUser(user);
        return boardRepository.save(board);
    }
}
