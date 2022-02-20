package com.Easy.SpringSecurity.controller;

import com.Easy.SpringSecurity.model.Board;
import com.Easy.SpringSecurity.repository.BoardRepository;
import com.Easy.SpringSecurity.service.BoardService;
import com.Easy.SpringSecurity.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 2) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText){
        //페이징 처리
        //Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText,searchText,pageable);
        int startPage = Math.max(1,boards.getPageable().getPageNumber() -4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() +4);
        boards.getPageable().getPageNumber();
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boards",boards);

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
