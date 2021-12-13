package com.jinho.homepage.service;

import com.jinho.homepage.dto.BoardResDto;
import com.jinho.homepage.dto.BoardSaveReqDto;
import com.jinho.homepage.dto.BoardUpdateDto;
import com.jinho.homepage.entity.BoardEntity;
import com.jinho.homepage.entity.UserEntity;
import com.jinho.homepage.repository.BoardRepository;
import com.jinho.homepage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    /* 게시글 - 목록 조회 */
    @Transactional(readOnly = true)
    public List<BoardResDto> boardList() {
        return boardRepository.findAll()
                .stream()
                .map(BoardResDto::new)
                .collect(Collectors.toList());
    }

    /* 게시글 - 상세 조회 */
    @Transactional(readOnly = true)
    public BoardResDto findById(Long boardSeq) {
        BoardEntity boardEntity = boardRepository.findById(boardSeq)
                .orElseThrow(() -> new IllegalAccessError("[boardSeq=" + boardSeq + "] 해당 게시글이 존재하지 않습니다."));

        return new BoardResDto(boardEntity);
    }

    /* 게시글 - 등록 */
    @Transactional
    public Long boardSave(BoardSaveReqDto boardSaveReqDto, String email) {
        UserEntity userEntity = userRepository.findByEmail(email).get();
        boardSaveReqDto.setNickname(userEntity.getNickname());

        return boardRepository.save(boardSaveReqDto.toEntity(userEntity)).getBoardSeq();
    }

    /* 게시글 - 수정 */
    @Transactional
    public Long boardUpdate(Long boardSeq, BoardUpdateDto boardUpdateDto) {
        BoardEntity boardEntity = boardRepository.findById(boardSeq)
                .orElseThrow(() -> new IllegalAccessError("[boardSeq=" + boardSeq + "] 해당 게시글이 존재하지 않습니다."));

        boardEntity.boardUpdate(boardUpdateDto.getTitle(), boardUpdateDto.getContent());

        return boardSeq;
    }

    /* 게시글 - 삭제 */
    @Transactional
    public void boardDelete(Long boardSeq) {
        BoardEntity boardEntity = boardRepository.findById(boardSeq)
                .orElseThrow(() -> new IllegalAccessError("[boardSeq=" + boardSeq + "] 해당 게시글이 존재하지 않습니다."));

        boardRepository.delete(boardEntity);
    }


}
