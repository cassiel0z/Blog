package com.example.blog_springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog_springboot.pojo.Travel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TravelMapper extends BaseMapper<Travel> {

}
