package com.example.blog_springboot.service;

import com.example.blog_springboot.mapper.PeopleMapper;
import com.example.blog_springboot.pojo.People;
import com.example.blog_springboot.utils.ImageUtil;
import com.example.blog_springboot.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class PeopleService {

    @Autowired
    private PeopleMapper peopleMapper;

    public ResultVO<Object> addArticle(String topic, String content, MultipartFile image, long userId) {
        ResultVO<Object> check = ImageUtil.checkImage(image);
        if (check != null) {
            return check;
        }

        try {
            People people = new People(null, topic, content, null, image.getBytes(), null, userId);
            peopleMapper.insert(people);
            return new ResultVO<>().SUCCESS("创建成功", people);
        } catch (Exception e) {
            return new ResultVO<>().FAIL("创建失败");
        }
    }

    public ResultVO<Object> getArticle(long id) {
        try {
            return new ResultVO<>().SUCCESS("查询成功", peopleMapper.selectById(id));
        } catch (Exception e) {
            return new ResultVO<>().FAIL("查询失败");
        }
    }

    public ResultVO<Object> getArticleByUser(long id) {
        try {
            Map<String, Object> searchingMap = new HashMap<>();
            searchingMap.put("userId", id);
            return new ResultVO<>().SUCCESS("查询成功", peopleMapper.selectByMap(searchingMap));
        } catch (Exception e) {
            return new ResultVO<>().FAIL("查询失败");
        }
    }

    public ResultVO<Object> deleteArticle(long id) {
        try {
            peopleMapper.deleteById(id);
            return new ResultVO<>().SUCCESS("删除成功", null);
        } catch (Exception e) {
            return new ResultVO<>().FAIL("删除失败");
        }
    }

}
