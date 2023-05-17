package com.example.blog_springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog_springboot.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
