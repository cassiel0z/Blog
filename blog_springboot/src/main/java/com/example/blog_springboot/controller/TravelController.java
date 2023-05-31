package com.example.blog_springboot.controller;

import com.example.blog_springboot.mapper.TravelMapper;
import com.example.blog_springboot.pojo.Travel;
import com.example.blog_springboot.service.TravelService;
import com.example.blog_springboot.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@RestController
@RequestMapping("/api/travel")
public class TravelController {

    @Autowired
    private TravelService travelService;

    @Autowired
    private TravelMapper travelMapper;

    @PostMapping("/add")
    public ResultVO<Object> addArticle(@RequestParam("topic") String topic, @RequestParam("content") String content, @RequestParam("image") MultipartFile image, @RequestParam("userId") long userId) {
        return travelService.addArticle(topic, content, image, userId);
    }

    @GetMapping("/get/{id}")
    public ResultVO<Object> getArticle(@PathVariable long id) {
        return travelService.getArticle(id);
    }

    @GetMapping("/get/user/{id}")
    public ResultVO<Object> getArticleByUser(@PathVariable long id) {
        return travelService.getArticleByUser(id);
    }

    @GetMapping("/delete/{id}")
    public ResultVO<Object> deleteArticle(@PathVariable long id) {
        return travelService.deleteArticle(id);
    }

    @GetMapping("/photo/get/{id}")
    public void getUserPhoto(@PathVariable long id, HttpServletResponse response) {
        try {
            Travel result = travelMapper.selectById(id);
            InputStream inputStream = new ByteArrayInputStream(result.getImage());

            ServletOutputStream outputStream = response.getOutputStream();
            int length = 0;
            byte[] buffer = new byte[1024];
            while ((length = inputStream.read(buffer, 0, 1024)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
        }
    }

    @PostMapping("/update")
    public ResultVO<Object> updateArticle(@RequestParam("id") Long id,
                                          @RequestParam(value = "topic", required = false) String topic,
                                          @RequestParam(value = "content", required = false) String content,
                                          @RequestParam(value = "image", required = false) MultipartFile image) {
        return travelService.updateArticle(id, topic, content, image);
    }

}
