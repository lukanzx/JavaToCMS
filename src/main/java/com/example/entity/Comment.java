package com.example.entity;

import java.sql.Timestamp;

/*
-- 创建评论表
CREATE TABLE `comment`
(
    `comment_id`      INT AUTO_INCREMENT PRIMARY KEY,
    `comment_content` TEXT         NOT NULL,
    `creation_time`   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `article_id`      INT          NOT NULL,
    `creator_id`      INT          NOT NULL,
    `article_title`   VARCHAR(255) NOT NULL,
    FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`),
    FOREIGN KEY (`creator_id`) REFERENCES `user` (`user_id`)
);
*/
public class Comment {
    private int commentId;
    private String commentContent;       // 评论内容
    private Timestamp creationTime;      // 创建时间
    private int articleId;               // 所属文章id
    private int creatorId;               // 创建者id
    private String articleTitle;         // 所属文章的标题

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

}
