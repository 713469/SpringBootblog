package com.example.blog.entity;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Comment {
    private Long id;
    private Long articleId;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;

    // 显示用字段
    private String userNickname;

    // Getter / Setter 省略
}
