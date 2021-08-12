package com.jinho.homepage.dto;

import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String nickName;
    private String password;

    private String authority;

}
