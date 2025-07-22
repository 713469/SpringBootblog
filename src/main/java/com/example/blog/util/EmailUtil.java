package com.example.blog.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerifyCode(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("博客验证码");
        message.setText("你的验证码是：" + code + "，有效期5分钟！");
        message.setTo(to);
        message.setFrom("2131644875@qq.com");
        mailSender.send(message);
    }
}
