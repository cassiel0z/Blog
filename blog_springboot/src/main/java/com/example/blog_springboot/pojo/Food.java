package com.example.blog_springboot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food {

    private Long id;
    private String topic;
    private String content;
    private Date createAt;
    private byte[] image;
    private Date updateAt;
    private Long userId;

}
