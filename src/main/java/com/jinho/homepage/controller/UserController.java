package com.jinho.homepage.controller;

import com.jinho.homepage.dto.UserDto;
import com.jinho.homepage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String getLogin() {
        return "/login";
    }

    @GetMapping("/signup")
    public String getSignup() {
        return "/signup";
    }

    @PostMapping("/signup")
    public String signup(UserDto userDto) {
        userService.userSave(userDto);
        return "redirect:/login";
    }

}
