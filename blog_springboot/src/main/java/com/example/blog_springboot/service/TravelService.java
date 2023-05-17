package com.example.blog_springboot.service;

import com.example.blog_springboot.mapper.TravelMapper;
import com.example.blog_springboot.pojo.Travel;
import com.example.blog_springboot.utils.ImageUtil;
import com.example.blog_springboot.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TravelService {

    @Autowired
    private TravelMapper travelMapper;

    public ResultVO<Object> addArticle(String topic, String content, MultipartFile image, long userId) {
        ResultVO<Object> check = ImageUtil.checkImage(image);
        if (check != null) {
            return check;
        }

        try {
            Travel travel = new Travel(null, topic, content, null, image.getBytes(), null, userId);
            travelMapper.insert(travel);
            return new ResultVO<>().SUCCESS("创建成功", travel);
        } catch (Exception e) {
            return new ResultVO<>().FAIL("创建失败");
        }
    }

    public ResultVO<Object> getArticle(long id) {
        try {
            return new ResultVO<>().SUCCESS("查询成功", travelMapper.selectById(id));
        } catch (Exception e) {
            return new ResultVO<>().FAIL("查询失败");
        }
    }

    public ResultVO<Object> deleteArticle(long id) {
        try {
            travelMapper.deleteById(id);
            return new ResultVO<>().SUCCESS("删除成功", null);
        } catch (Exception e) {
            return new ResultVO<>().FAIL("删除失败");
        }
    }

}
