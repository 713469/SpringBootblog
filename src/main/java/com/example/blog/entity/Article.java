package com.example.blog.entity;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Article {
    private String authorNickname;

    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private LocalDateTime createdAt;

    // Getter / Setter 省略，也可使用 Lombok
}
