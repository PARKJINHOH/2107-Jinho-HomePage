package com.jinho.homepage.controller;

import com.jinho.homepage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UtilController {

    private final UserService userService;

    @GetMapping("/confirm-email")
    public String viewConfirmEmail(@Valid @RequestParam String token) throws Exception {
        userService.confirmEmail(token);

        return "redirect:/login";
    }

}
