package com.example.blog_springboot.service;

import com.example.blog_springboot.mapper.FoodMapper;
import com.example.blog_springboot.pojo.Food;
import com.example.blog_springboot.utils.ImageUtil;
import com.example.blog_springboot.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class FoodService {

    @Autowired
    private FoodMapper foodMapper;

    public ResultVO<Object> addArticle(String topic, String content, MultipartFile image, long userId) {
        ResultVO<Object> check = ImageUtil.checkImage(image);
        if (check != null) {
            return check;
        }

        try {
            Food food = new Food(null, topic, content, null, image.getBytes(), null, userId);
            foodMapper.insert(food);
            return new ResultVO<>().SUCCESS("创建成功", food);
        } catch (Exception e) {
            return new ResultVO<>().FAIL("创建失败");
        }
    }

    public ResultVO<Object> getArticle(long id) {
        try {
            return new ResultVO<>().SUCCESS("查询成功", foodMapper.selectById(id));
        } catch (Exception e) {
            return new ResultVO<>().FAIL("查询失败");
        }
    }

    public ResultVO<Object> getArticleByUser(long id) {
        try {
            Map<String, Object> searchingMap = new HashMap<>();
            searchingMap.put("userId", id);
            return new ResultVO<>().SUCCESS("查询成功", foodMapper.selectByMap(searchingMap));
        } catch (Exception e) {
            return new ResultVO<>().FAIL("查询失败");
        }
    }

    public ResultVO<Object> deleteArticle(long id) {
        try {
            foodMapper.deleteById(id);
            return new ResultVO<>().SUCCESS("删除成功", null);
        } catch (Exception e) {
            return new ResultVO<>().FAIL("删除失败");
        }
    }

    public ResultVO<Object> updateArticle(long id, String topic, String content, MultipartFile image) {
        if (image != null) {
            ResultVO<Object> check = ImageUtil.checkImage(image);
            if (check != null) {
                return check;
            }
        }

        try {
            Food food = new Food(id, topic, content, null, image == null ? null : image.getBytes(), null, null);
            foodMapper.updateById(food);
            return new ResultVO<>().SUCCESS("修改成功", food);
        } catch (Exception e) {
            return new ResultVO<>().FAIL("修改失败");
        }
    }

}
