package com.example.blog.controller;

import com.example.blog.entity.User;
import com.example.blog.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/send-code")
    public String sendCode(@RequestParam String email) {
        authService.sendCode(email);
        return "验证码已发送";
    }

    @PostMapping("/register")
    public String register(@RequestParam String email,
                           @RequestParam String code,
                           @RequestParam String nickname,
                           @RequestParam String password,
                           HttpSession session) {
        boolean success = authService.register(email, code, nickname, password);
        if (success) {
            session.setAttribute("loginUser", email);
            return "注册成功";
        } else {
            return "注册失败：验证码无效或用户已存在";
        }
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam String email,
                                     @RequestParam String password,
                                     HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        User user = authService.loginAndReturnUser(email, password);
        if (user != null) {
            session.setAttribute("loginUser", email);
            result.put("msg", "登录成功");
            result.put("nickname", user.getNickname());
            result.put("email", user.getEmail());
        } else {
            result.put("msg", "登录失败：账号或密码错误");
        }
        return result;
    }
}
