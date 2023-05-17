package com.example.blog_springboot.service;

import com.example.blog_springboot.mapper.UserMapper;
import com.example.blog_springboot.pojo.User;
import com.example.blog_springboot.utils.*;
import com.example.blog_springboot.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private VerifyCodeUtil verifyCodeUtil;

    @Autowired
    private MailService mailService;

    private User searchUserByEmail(String email) {
        Map<String, Object> searchingMap = new HashMap<>();
        searchingMap.put("email", email);
        List<User> result = userMapper.selectByMap(searchingMap);

        if (result.size() == 0) {
            return null;
        }
        return result.get(0);
    }

    public ResultVO<Object> register(String email, String password, String username, String code) {
        User result = searchUserByEmail(email);

        if (result != null) {
            return new ResultVO<>().FAIL("用户已存在");
        }
        if (!verifyCodeUtil.verifyCode(email, code)) {
            return new ResultVO<>().FAIL("验证码错误");
        }

        try {
            result = new User(null, email, HashUtil.getSHA256(password), username, null, null);
            userMapper.insert(result);
            return loginByPassword(email, password);
        } catch (Exception e) {
            return new ResultVO<>().FAIL("用户注册失败");
        }
    }

    public ResultVO<Object> loginByPassword(String email, String password) {
        User result = searchUserByEmail(email);

        if (result == null) {
            return new ResultVO<>().FAIL("用户不存在");
        }
        if (!HashUtil.isHashSame(password, result.getPassword())) {
            return new ResultVO<>().FAIL("密码错误");
        }

        String token = JwtUtil.sign(email);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        resultMap.put("id", result.getId());

        if (token != null) {
            return new ResultVO<>().SUCCESS("用户登陆成功", resultMap);
        } else {
            return new ResultVO<>().FAIL("Token生成失败");
        }
    }

    public ResultVO<Object> loginByCode(String email, String code) {
        User result = searchUserByEmail(email);

        if (result == null) {
            return new ResultVO<>().FAIL("用户不存在");
        }
        if (!verifyCodeUtil.verifyCode(email, code)) {
            return new ResultVO<>().FAIL("验证码错误");
        }

        String token = JwtUtil.sign(email);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", result.getId());
        resultMap.put("token", token);

        if (token != null) {
            return new ResultVO<>().SUCCESS("用户登陆成功", resultMap);
        } else {
            return new ResultVO<>().FAIL("Token生成失败");
        }
    }

    public ResultVO<Object> resetPassword(String email, String password, String code) {
        User result = searchUserByEmail(email);

        if (result == null) {
            return new ResultVO<>().FAIL("用户不存在");
        }
        if (!verifyCodeUtil.verifyCode(email, code)) {
            return new ResultVO<>().FAIL("验证失败");
        }

        try {
            userMapper.updateById(new User(result.getId(), null, HashUtil.getSHA256(password), null, null, null));
        } catch (Exception e) {
            return new ResultVO<>().FAIL("修改密码失败");
        }

        return new ResultVO<>().SUCCESS("修改密码成功", null);
    }

    public ResultVO<Object> resetEmail(Long id, String email, String code) {
        if (!verifyCodeUtil.verifyCode(email, code)) {
            return new ResultVO<>().FAIL("验证失败");
        }

        try {
            userMapper.updateById(new User(id, email, null, null, null, null));
        } catch (Exception e) {
            return new ResultVO<>().FAIL("修改邮箱失败");
        }

        String token = JwtUtil.sign(email);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        resultMap.put("token", token);
        return new ResultVO<>().SUCCESS("修改邮箱成功", resultMap);
    }

    public ResultVO<Object> sendCodeWhenLogin(String email) {
        try {
            User result = searchUserByEmail(email);
            if (result == null) {
                return new ResultVO<>().FAIL("用户不存在");
            }

            String code = verifyCodeUtil.getCode(email);
            if (code != null) {
                mailService.sendVerifyCode(email, code);
                return new ResultVO<>().SUCCESS("获取验证码成功", null);
            }
        } catch (Exception ignored) {

        }
        return new ResultVO<>().FAIL("获取验证码失败");
    }

    public ResultVO<Object> sendCodeWhenRegister(String email) {
        try {
            User result = searchUserByEmail(email);
            if (result != null) {
                return new ResultVO<>().FAIL("用户已注册");
            }

            String code = verifyCodeUtil.getCode(email);
            if (code != null) {
                mailService.sendVerifyCode(email, code);
                return new ResultVO<>().SUCCESS("获取验证码成功", null);
            }
        } catch (Exception ignored) {

        }
        return new ResultVO<>().FAIL("获取验证码失败");
    }

    public ResultVO<Object> sendCodeWhenChangingEmail(String email) {
        try {
            User result = searchUserByEmail(email);
            if (result != null) {
                return new ResultVO<>().FAIL("邮箱已占用");
            }

            String code = verifyCodeUtil.getCode(email);
            if (code != null) {
                mailService.sendVerifyCode(email, code);
                return new ResultVO<>().SUCCESS("获取验证码成功", null);
            }
        } catch (Exception ignored) {

        }
        return new ResultVO<>().FAIL("获取验证码失败");
    }

    public ResultVO<Object> getUserInfo(long id) {
        try {
            User user = userMapper.selectById(id);

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("id", id);
            resultMap.put("email", user.getEmail());
            resultMap.put("username", user.getUsername());

            return new ResultVO<>().SUCCESS("用户信息获取成功", resultMap);
        } catch (Exception e) {
            return new ResultVO<>().FAIL("用户信息获取失败");
        }
    }

    public ResultVO<Object> uploadUserPhoto(long id, MultipartFile image) {
        try {
            ResultVO<Object> check = ImageUtil.checkImage(image);
            if (check != null) {
                return check;
            }

            User user = new User(id, null, null, null, image.getBytes(), ImageUtil.getImageType(image));
            userMapper.updateById(user);
            return new ResultVO<>().SUCCESS("修改头像成功", null);
        } catch (Exception e) {
            return new ResultVO<>().FAIL("修改头像失败");
        }
    }

    public ResultVO<Object> updateUsername(long id, String username) {
        try {
            User user = new User(id, null, null, username, null, null);
            userMapper.updateById(user);
            return new ResultVO<>().SUCCESS("修改用户名成功", null);
        } catch (Exception e) {
            return new ResultVO<>().FAIL("修改用户名失败");
        }
    }

}
