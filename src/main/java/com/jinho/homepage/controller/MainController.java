package com.jinho.homepage.controller;

import com.jinho.homepage.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/")
    public String getMainPage() {
        return "/mainPage/mainPage";
    }

    @GetMapping("/forum")
    public String getForumPage() {
        return "/subPage/forumPage";
    }

}
