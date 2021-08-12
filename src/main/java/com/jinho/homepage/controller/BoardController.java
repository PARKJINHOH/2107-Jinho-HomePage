package com.jinho.homepage.controller;

import com.jinho.homepage.dto.BoardResDto;
import com.jinho.homepage.dto.BoardSaveReqDto;
import com.jinho.homepage.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    // https://tychejin.tistory.com/191
    // https://victorydntmd.tistory.com/327?category=764331

    private final BoardService boardService;

    /*자유게시판 - 목록*/
    @GetMapping("/forum")
    public String boardList(Model model) {
        List<BoardResDto> boardList = boardService.boardList();
        model.addAttribute("boardList", boardList);

        return "/subPage/forum";
    }

    /*자유게시판 - 조회*/
    @GetMapping("/forum/{boardSeq}")
    public BoardResDto getFindBoard(@PathVariable("boardSeq") Long boardSeq) {
        return boardService.findById(boardSeq);
    }

    /*자유게시판 - 작성*/
    @GetMapping("/forum/write")
    public String getBoard() {
        return "/subPage/forumCreate";
    }

    @PostMapping("/forum/write")
    public void saveBoard(@RequestBody BoardSaveReqDto boardSaveReqDto) {
        boardService.boardSave(boardSaveReqDto);
    }

}
