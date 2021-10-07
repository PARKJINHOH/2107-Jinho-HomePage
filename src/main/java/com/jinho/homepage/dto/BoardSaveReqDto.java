package com.jinho.homepage.dto;

import com.jinho.homepage.entity.BoardEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardSaveReqDto {

    private String writer;
    private String title;
    private String content;

    @Builder
    public BoardSaveReqDto(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public BoardEntity toEntity() {
        return BoardEntity.builder()
                .writer(writer)
                .title(title)
                .content(content)
                .build();
    }
}
