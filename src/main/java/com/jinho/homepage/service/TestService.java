package com.jinho.homepage.service;

import com.jinho.homepage.mapper.TestMapper;
import com.jinho.homepage.vo.testVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    private TestMapper testMapper;

    public TestService(TestMapper testMapper) {
        this.testMapper = testMapper;
    }

    public List<testVo> selectTest(){
        return testMapper.selectTest();
    }
}
