package com.example.blog.service;

import com.example.blog.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByArticle(Long articleId);
    void addComment(Comment comment);
}
