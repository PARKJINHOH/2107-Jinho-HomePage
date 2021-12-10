package com.jinho.homepage.dto;

import com.jinho.homepage.entity.UserEntity;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    // 인증된 사용자 정보를 위한 dto

    private String email;
    private String nickname;

    public SessionUser(UserEntity user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}
