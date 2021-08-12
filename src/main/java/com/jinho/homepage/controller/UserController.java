package com.jinho.homepage.controller;

import com.jinho.homepage.dto.UserDto;
import com.jinho.homepage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @PostMapping("/singup")
    public String singup(UserDto userDto) {
        userService.userSave(userDto);
        return "redirect:/login";
    }

//    @GetMapping("/logout")
//    public String getLogout(HttpServletRequest request, HttpServletResponse response) {
//        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
//        return "redirect:/login";
//    }

}
