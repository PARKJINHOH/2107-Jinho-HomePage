package com.jinho.homepage.controller;

import com.jinho.homepage.service.TestService;
import com.jinho.homepage.vo.TestVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    private TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/test")
    public List<TestVo> testList(){
        return testService.selectTest();
    }
}
