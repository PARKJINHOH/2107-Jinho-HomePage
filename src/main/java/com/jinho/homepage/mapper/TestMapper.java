package com.jinho.homepage.mapper;

import com.jinho.homepage.vo.testVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TestMapper {
    List<testVo> selectTest();
}
