package com.example.blog.service.impl;

import com.example.blog.entity.User;
import com.example.blog.mapper.UserMapper;
import com.example.blog.service.AuthService;
import com.example.blog.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private UserMapper userMapper;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void sendCode(String email) {
        String code = String.valueOf(new Random().nextInt(899999) + 100000);
        redisTemplate.opsForValue().set("code:" + email, code, Duration.ofMinutes(5));
        emailUtil.sendVerifyCode(email, code);
    }

    @Override
    public boolean register(String email, String code, String nickname, String password) {
        String cachedCode = redisTemplate.opsForValue().get("code:" + email);
        if (cachedCode == null || !cachedCode.equals(code)) return false;

        if (userMapper.findByEmail(email) != null) return false;

        String encodedPassword = encoder.encode(password);

        User user = new User();
        user.setEmail(email);
        user.setNickname(nickname);
        user.setPassword(encodedPassword);
        userMapper.insertUser(user);  // 自动生成主键会赋值到 user.id

        redisTemplate.delete("code:" + email);
        return true;
    }
    @Override
    public User loginAndReturnUser(String email, String password) {
        User user = userMapper.findByEmail(email);
        if (user != null && encoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }


    @Override
    public boolean login(String email, String password) {
        User user = userMapper.findByEmail(email);
        return user != null && encoder.matches(password, user.getPassword());
    }

    @Override
    public boolean verifyCode(String email, String code) {
        String redisCode = redisTemplate.opsForValue().get("code:" + email);
        return code != null && code.equals(redisCode);
    }
}

