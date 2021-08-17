package com.jinho.homepage.controller;

import com.jinho.homepage.dto.BoardResDto;
import com.jinho.homepage.dto.BoardSaveReqDto;
import com.jinho.homepage.entity.UploadFileEntity;
import com.jinho.homepage.service.BoardService;
import com.jinho.homepage.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    // https://tychejin.tistory.com/191
    // https://victorydntmd.tistory.com/327?category=764331

    private final BoardService boardService;
    private final ImageService imageService;
    private final ResourceLoader resourceLoader;

    /*자유게시판 - 목록*/
    @GetMapping("/forum")
    public String boardList(Model model) {
        List<BoardResDto> boardList = boardService.boardList();
        model.addAttribute("boardList", boardList);

        return "/subPage/forum";
    }

    /*자유게시판 - 상세조회*/
    @GetMapping("/forum/{boardSeq}")
    public String getFindBoard(Model model, @PathVariable("boardSeq") Long boardSeq) {
        BoardResDto board = boardService.findById(boardSeq);

        model.addAttribute("board", board);
        return "/subPage/forumDetail";
    }

    /*자유게시판 - 작성*/
    @GetMapping("/forum/write")
    public String getBoard() {
        return "/subPage/forumCreate";
    }

    @PostMapping("/forum/write")
    public String saveBoard(BoardSaveReqDto boardSaveReqDto, Principal principal) {
        boardSaveReqDto.setWriter(principal.getName());
        Long boardSeq = boardService.boardSave(boardSaveReqDto);
        return "redirect:/forum";
    }

    /* 이미지 저장 및 불러오기 */
    @PostMapping("/image")
    public ResponseEntity<?> imageUpload(@RequestParam("file") MultipartFile file) {
        try {
            UploadFileEntity uploadFile = imageService.store(file);
            return ResponseEntity.ok().body("/image/" + uploadFile.getFileSeq());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/image/{fileSeq}")
    public ResponseEntity<?> serveFile(@PathVariable Long fileSeq) {
        try {
            UploadFileEntity uploadFile = imageService.load(fileSeq);
            Resource resource = resourceLoader.getResource("file:" + uploadFile.getFilePath());
            return ResponseEntity.ok().body(resource);
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


    /* 자유게시판 - 삭제 */
    @PostMapping("/forum/delete")
    public ResponseEntity<Long> boardDelete(@RequestBody BoardResDto boardResDto){

        System.out.println("getBoardSeq : " + boardResDto.getBoardSeq());

        Long boardSeq = boardResDto.getBoardSeq();

        String currentUserName = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }

        String writer = boardService.findById(boardSeq).getWriter();

        if (currentUserName != null && currentUserName.equals(writer)) {
            boardService.boardDelete(boardSeq);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
