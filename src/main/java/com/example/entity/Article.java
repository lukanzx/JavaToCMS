package com.example.entity;

import java.sql.Timestamp;

/*
-- 创建文章表
CREATE TABLE `article`
(
    `article_id`      INT AUTO_INCREMENT PRIMARY KEY,
    `article_title`   VARCHAR(255) NOT NULL,
    `article_content` TEXT         NOT NULL,
    `creation_time`   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `update_time`     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `topic_id`        INT          NOT NULL,
    `creator_id`      INT          NOT NULL,
    `cover_image_id`  INT       DEFAULT 10000,
    FOREIGN KEY (`topic_id`) REFERENCES `topic` (`topic_id`),
    FOREIGN KEY (`creator_id`) REFERENCES `user` (`user_id`),
    FOREIGN KEY (`cover_image_id`) REFERENCES `images` (`image_id`)
);
*/
public class Article {
    private int articleId;
    private String articleTitle;         // 文章标题
    private String articleContent;       // 文章内容
    private Timestamp creationTime;      // 创建时间
    private Timestamp updateTime;        // 更新时间
    private int topicId;                 // 所属专题id
    private int creatorId;               // 创建者id
    private int coverImageId;            // 封面图片id

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getCoverImageId() {
        return coverImageId;
    }

    public void setCoverImageId(int coverImageId) {
        this.coverImageId = coverImageId;
    }


}
