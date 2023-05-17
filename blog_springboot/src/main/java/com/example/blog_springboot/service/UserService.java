package com.example.blog_springboot.service;

import com.example.blog_springboot.mapper.UserMapper;
import com.example.blog_springboot.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public ResultVO<Object> getUserById(long id) {
        try {
            return new ResultVO<>().SUCCESS("success", userMapper.selectById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVO<>().FAIL("fail", null);
        }
    }

}
