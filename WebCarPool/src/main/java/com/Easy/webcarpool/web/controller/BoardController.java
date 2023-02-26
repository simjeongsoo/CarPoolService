package com.Easy.webcarpool.web.controller;

import com.Easy.webcarpool.web.domain.Board;
import com.Easy.webcarpool.web.repository.BoardRepository;
import com.Easy.webcarpool.web.service.user.BoardService;
import com.Easy.webcarpool.web.validator.BoardValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;
    private final BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText) {
        //페이징 처리
        //Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);
        boards.getPageable().getPageNumber();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boards", boards);

        //boards.getTotalElements();
        model.addAttribute("boards", boards);
        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        if (id == null){
            model.addAttribute("board",new Board());
        }else {
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board",board);
        }
        return "board/form";
    }

    @PostMapping("/form")
    public String postFrom(@Valid Board board, BindingResult bindingResult, Authentication authentication) {
        boardValidator.validate(board,bindingResult);
        if (bindingResult.hasErrors()){
            return "board/form";
        }
        //Authentication a = SecurityContextHolder.getContext().getAuthentication(); // 인증정보를 이렇게 받아와도 됌
        String username = authentication.getName();
        //board.setUser(user); // user의 키값을 참조하여  user_id 값이 담김

        boardService.save(username,board); // boardService 에서 username까지 저장
//        boardRepository.save(board);
        return "redirect:/board/list";
    }

}
