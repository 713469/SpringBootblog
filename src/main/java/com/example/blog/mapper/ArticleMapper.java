package com.example.blog.mapper;

import com.example.blog.entity.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Select("SELECT * FROM articles ORDER BY created_at DESC")
    List<Article> findAll();

    @Select("SELECT * FROM articles WHERE id = #{id}")
    Article findById(@Param("id") Long id);

    @Select("SELECT a.id, a.title, a.content, a.created_at, u.nickname AS authorNickname " +
            "FROM articles a LEFT JOIN users u ON a.author_id = u.id ORDER BY a.created_at DESC")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "authorNickname", column = "authorNickname")
    })
    List<Article> findAllWithAuthor();

    @Insert("INSERT INTO articles(title, content, author_id) VALUES(#{title}, #{content}, #{authorId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertArticle(Article article);

    @Update("UPDATE articles SET title = #{title}, content = #{content} WHERE id = #{id}")
    void updateArticle(Article article);


}
