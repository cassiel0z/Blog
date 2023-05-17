package com.example.blog_springboot.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO<T> {

    private int code;
    private String message;
    private T data;

    public ResultVO<T> SUCCESS(String msg, T data) {
        this.code = 0;
        this.message = msg;
        this.data = data;

        return this;
    }

    public ResultVO<T> FAIL(String msg) {
        this.code = -1;
        this.message = msg;
        this.data = null;

        return this;
    }

}
