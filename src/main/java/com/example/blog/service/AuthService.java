package com.example.blog.service;

import com.example.blog.entity.User;

public interface AuthService {
    void sendCode(String email);
    boolean register(String email, String code, String nickname, String password);
    boolean verifyCode(String email, String code);
    boolean login(String email, String password);
    User loginAndReturnUser(String email, String password); // 新增
}
