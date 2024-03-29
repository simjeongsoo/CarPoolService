package com.Easy.webcarpool.web.service.user;

import com.Easy.webcarpool.web.domain.Board;
import com.Easy.webcarpool.web.domain.User;
import com.Easy.webcarpool.web.repository.BoardRepository;
import com.Easy.webcarpool.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
