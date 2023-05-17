package com.example.blog_springboot.Interceptor;

import com.example.blog_springboot.utils.JwtUtil;
import com.example.blog_springboot.vo.ResultVO;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String token = request.getHeader("authorization");
            if (token != null && JwtUtil.verify(token)) {
                return true;
            }
        } catch (Exception ignored) {

        }
        response.getWriter().append(new JSONObject(new ResultVO<>().FAIL("Please register or login")).toString());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return false;
    }
}
