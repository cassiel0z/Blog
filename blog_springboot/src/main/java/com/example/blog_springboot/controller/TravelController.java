package com.example.blog_springboot.controller;

import com.example.blog_springboot.service.TravelService;
import com.example.blog_springboot.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/travel")
public class TravelController {

    @Autowired
    private TravelService travelService;

    @PostMapping("/add")
    public ResultVO<Object> addArticle(@RequestParam("topic") String topic, @RequestParam("content") String content, @RequestParam("image") MultipartFile image, @RequestParam("userId") long userId) {
        return travelService.addArticle(topic, content, image, userId);
    }

    @GetMapping("/get/{id}")
    public ResultVO<Object> getArticle(@PathVariable long id) {
        return travelService.getArticle(id);
    }

}
