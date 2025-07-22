package com.example.blog.service.impl;

import com.example.blog.entity.Comment;
import com.example.blog.mapper.CommentMapper;
import com.example.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getCommentsByArticle(Long articleId) {
        return commentMapper.findByArticleId(articleId);
    }

    @Override
    public void addComment(Comment comment) {
        commentMapper.insertComment(comment);
    }
}
