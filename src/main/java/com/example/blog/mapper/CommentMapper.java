package com.example.blog.mapper;

import com.example.blog.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Select("SELECT c.*, u.nickname AS userNickname " +
            "FROM comments c LEFT JOIN users u ON c.user_id = u.id " +
            "WHERE article_id = #{articleId} ORDER BY c.created_at ASC")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "articleId", column = "article_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "content", column = "content"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "userNickname", column = "userNickname")
    })
    List<Comment> findByArticleId(Long articleId);

    @Insert("INSERT INTO comments(article_id, user_id, content) VALUES(#{articleId}, #{userId}, #{content})")
    void insertComment(Comment comment);
}
