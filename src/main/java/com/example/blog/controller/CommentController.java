package com.example.blog.controller;

import com.example.blog.entity.Comment;
import com.example.blog.entity.User;
import com.example.blog.mapper.UserMapper;
import com.example.blog.service.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/add")
    public String addComment(@RequestParam Long articleId,
                             @RequestParam String content,
                             HttpSession session) {
        String email = (String) session.getAttribute("loginUser");
        if (email == null) {
            return "未登录";
        }

        User user = userMapper.findByEmail(email);
        if (user == null) {
            return "用户不存在";
        }

        Comment comment = new Comment();
        comment.setArticleId(articleId);
        comment.setUserId(user.getId());
        comment.setContent(content);
        commentService.addComment(comment);
        return "评论成功";
    }

    @GetMapping("/{articleId}")
    public List<Comment> getComments(@PathVariable Long articleId) {
        return commentService.getCommentsByArticle(articleId);
    }
}
