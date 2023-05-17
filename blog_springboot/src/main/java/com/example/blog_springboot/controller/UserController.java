package com.example.blog_springboot.controller;

import com.example.blog_springboot.mapper.UserMapper;
import com.example.blog_springboot.pojo.User;
import com.example.blog_springboot.service.UserService;
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
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/register")
    public ResultVO<Object> register(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("username") String username, @RequestParam("verify") String verify) {
        return userService.register(email, password, username, verify);
    }

    @PostMapping("/register/sendCode")
    public ResultVO<Object> sendCodeWhenRegister(@RequestParam("email") String email) {
        return userService.sendCodeWhenRegister(email);
    }

    @PostMapping("/login/sendCode")
    public ResultVO<Object> sendCodeWhenLogin(@RequestParam("email") String email) {
        return userService.sendCodeWhenLogin(email);
    }

    @PostMapping("/email/reset/sendCode")
    public ResultVO<Object> sendCodeWhenChangingEmail(@RequestParam("email") String email) {
        return userService.sendCodeWhenChangingEmail(email);
    }

    @PostMapping("/login/password")
    public ResultVO<Object> loginByPassword(@RequestParam("email") String email, @RequestParam("password") String password) {
        return userService.loginByPassword(email, password);
    }

    @PostMapping("/login/code")
    public ResultVO<Object> loginByCode(@RequestParam("email") String email, @RequestParam("verify") String verify) {
        return userService.loginByCode(email, verify);
    }

    @GetMapping("/info/get/{id}")
    public ResultVO<Object> getUserInfo(@PathVariable long id) {
        return userService.getUserInfo(id);
    }

    @GetMapping("/photo/get/{id}")
    public void getUserPhoto(@PathVariable long id, HttpServletResponse response) {
        try {
            User result = userMapper.selectById(id);
            InputStream inputStream = new ByteArrayInputStream(result.getPhoto());
            response.setContentType(result.getPhotoType());

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

    @PostMapping("/photo/upload/{id}")
    public ResultVO<Object> uploadUserPhoto(@PathVariable long id, @RequestParam("photo") MultipartFile multipartFile) {
        return userService.uploadUserPhoto(id, multipartFile);
    }

    @PostMapping("/info/username/update/{id}")
    public ResultVO<Object> updateUsername(@PathVariable long id, @RequestParam("username") String username) {
        return userService.updateUsername(id, username);
    }

    @PostMapping("/password/reset")
    public ResultVO<Object> resetPassword(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("verify") String verify) {
        return userService.resetPassword(email, password, verify);
    }

    @PostMapping("/email/reset")
    public ResultVO<Object> resetEmail(@RequestParam("id") Long id, @RequestParam("email") String email, @RequestParam("verify") String verify) {
        return userService.resetEmail(id, email, verify);
    }

    @PostMapping("/logout")
    public ResultVO<Object> logout(@RequestParam("id") long id) {
        return new ResultVO<>(0, "退出登陆成功", null);
    }

}
