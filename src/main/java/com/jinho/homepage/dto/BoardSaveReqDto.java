package com.jinho.homepage.dto;

import com.jinho.homepage.entity.BoardEntity;
import com.jinho.homepage.entity.UserEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardSaveReqDto {

    private String nickname;
    private String title;
    private String content;

    @Builder
    public BoardSaveReqDto(String nickname, String title, String content) {
        this.nickname = nickname;
        this.title = title;
        this.content = content;
    }

    public BoardEntity toEntity(UserEntity userEntity) {
        return BoardEntity.builder()
                .nickname(nickname)
                .title(title)
                .content(content)
                .user(userEntity)
                .build();
    }
}
