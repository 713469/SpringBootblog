package com.example.blog.controller;

import com.example.blog.entity.Article;
import com.example.blog.entity.User;
import com.example.blog.mapper.ArticleMapper;
import com.example.blog.mapper.UserMapper;
import com.example.blog.service.ArticleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @GetMapping("/list")
    public List<Article> listArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable Long id) {
        return articleService.getArticleById(id);
    }


    @PostMapping("/add")
    public String addArticle(@RequestParam String title,
                             @RequestParam String content,
                             HttpSession session) {
        String email = (String) session.getAttribute("loginUser");
        if (email == null) {
            return "未登录";
        }

        // 权限限制：只允许管理员邮箱发布文章
        if (!"2131644875@qq.com".equals(email)) {
            return "无权限操作";
        }

        User user = userMapper.findByEmail(email);
        if (user == null) {
            return "用户不存在";
        }

        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setAuthorId(user.getId());
        articleMapper.insertArticle(article);
        return "发布成功";
    }


    @PostMapping("/update")
    public String updateArticle(@RequestBody Map<String, String> data,
                                HttpSession session) {

        String email = (String) session.getAttribute("loginUser");
        if (email == null) {
            return "未登录";
        }
        if (!"2131644875@qq.com".equals(email)) {
            return "无权限操作";
        }

        Long id = Long.parseLong(data.get("id"));
        String title = data.get("title");
        String content = data.get("content");

        Article article = articleMapper.findById(id);
        if (article == null) {
            return "文章不存在";
        }

        article.setTitle(title);
        article.setContent(content);
        articleMapper.updateArticle(article);
        return "修改成功";
    }
}
