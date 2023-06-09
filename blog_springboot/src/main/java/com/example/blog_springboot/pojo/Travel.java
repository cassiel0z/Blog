package com.example.blog_springboot.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Travel {

    private Long id;
    private String topic;
    private String content;
    private Date createAt;
    @JsonIgnore
    private byte[] image;
    private Date updateAt;
    private Long userId;

}
