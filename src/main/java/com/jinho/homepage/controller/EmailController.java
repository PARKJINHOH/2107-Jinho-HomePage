package com.jinho.homepage.controller;

import com.jinho.homepage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class EmailController {

    private final UserService userService;

    /**
     * 이메일 인증 수신
     * @param token
     * @return
     * @throws Exception
     */
    @GetMapping("/confirm-email")
    public String viewConfirmEmail(@Valid @RequestParam String token) {
        userService.confirmEmail(token);

        return "redirect:/login";
    }

}
