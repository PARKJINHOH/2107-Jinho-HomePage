package com.jinho.homepage.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardUpdateDto {

    private String title;
    private String content;

    @Builder
    public BoardUpdateDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
