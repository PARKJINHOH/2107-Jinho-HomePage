package com.jinho.homepage.service.email;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConfirmationTokenServiceTest {


    @Autowired
    private ConfirmationTokenService confirmationTokenService;


    @Test
    public void generateAuthToken(){
        String token = confirmationTokenService.generateAuthToken();
        System.out.println("token : " + token);
    }




}