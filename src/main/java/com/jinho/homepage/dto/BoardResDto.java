package com.jinho.homepage.dto;

import com.jinho.homepage.entity.BoardEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardResDto {

    private Long boardSeq;
    private String email; // 수정&삭제 비교변수
    private String nickname;
    private String title;
    private String content;

    public BoardResDto(BoardEntity board) {
        this.email = board.getUser().getEmail();
        this.boardSeq = board.getBoardSeq();
        this.nickname = board.getNickname();
        this.title = board.getTitle();
        this.content = board.getContent();
    }
}
