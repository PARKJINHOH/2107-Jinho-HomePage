package com.jinho.homepage.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "tbl_board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardEntity {

    @Id
    @Column(name = "board_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardSeq;

    @Column(name = "board_writer")
    private String writer;

    @Column(name = "board_title")
    private String title;

    @Lob // 가변길이를 가지는 큰 데이터를 저장하는데 사용하는 데이터형
    @Column(name = "board_content")
    private String content;

    @Builder
    public BoardEntity(Long boardSeq, String writer, String title, String content) {
        this.boardSeq = boardSeq;
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public void boardUpdate(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
