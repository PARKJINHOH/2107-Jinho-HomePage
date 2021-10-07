package com.jinho.homepage.dto;

import com.jinho.homepage.entity.BoardEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardResDto {

    private Long boardSeq;
    private String writer;
    private String title;
    private String content;

    public BoardResDto(BoardEntity board) {
        this.boardSeq = board.getBoardSeq();
        this.writer = board.getWriter();
        this.title = board.getTitle();
        this.content = board.getContent();
    }
}
