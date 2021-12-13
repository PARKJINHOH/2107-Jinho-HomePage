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

    @Column(name = "board_nickname")
    private String nickname;

    @Column(name = "board_title")
    private String title;

    @Lob // 가변길이를 가지는 큰 데이터를 저장하는데 사용하는 데이터형
    @Column(name = "board_content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private UserEntity user;

    @Builder
    public BoardEntity(String nickname, String title, String content, UserEntity user) {
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void boardUpdate(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
