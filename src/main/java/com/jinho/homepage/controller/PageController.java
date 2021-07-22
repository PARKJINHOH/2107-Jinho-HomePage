package com.jinho.homepage.controller;

import com.jinho.homepage.service.PageService;
import com.jinho.homepage.vo.testVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PageController {

    private PageService pageService;

    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @GetMapping("/test")
    public List<testVo> testList(){
        return pageService.selectTest();
    }
}
