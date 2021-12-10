package com.jinho.homepage.dto;

import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String nickname;
    private String password;

    private String authority;

}
