package com.example.blog_springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog_springboot.pojo.People;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PeopleMapper extends BaseMapper<People> {

}
