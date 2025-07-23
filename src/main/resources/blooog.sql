CREATE DATABASE blooog;
       USE blooog;
CREATE TABLE users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                       nickname VARCHAR(100) DEFAULT '匿名',
                       password VARCHAR(255) NOT NULL
);
CREATE TABLE articles (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          title VARCHAR(255) NOT NULL,
                          content LONGTEXT,
                          author_id BIGINT NOT NULL,
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE comments (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          article_id BIGINT NOT NULL,
                          user_id BIGINT NOT NULL,
                          content TEXT NOT NULL,
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
