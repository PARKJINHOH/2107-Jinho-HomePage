package com.jinho.homepage.service;

import com.jinho.homepage.mapper.PageMapper;
import com.jinho.homepage.vo.testVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageService {

    private PageMapper pageMapper;

    public PageService(PageMapper pageMapper) {
        this.pageMapper = pageMapper;
    }

    public List<testVo> selectTest(){
        return pageMapper.selectTest();
    }
}
