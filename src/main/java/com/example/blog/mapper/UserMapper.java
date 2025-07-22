package com.example.blog.mapper;

import com.example.blog.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("SELECT id, email, nickname, password, created_at FROM users WHERE email = #{email}")
    User findByEmail(@Param("email") String email);

    @Insert("INSERT INTO users(email, nickname, password) VALUES(#{email}, #{nickname}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);
}
