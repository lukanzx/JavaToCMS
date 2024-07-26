package com.example.entity;

import java.sql.Timestamp;

/*
CREATE TABLE posters (
    poster_id INT AUTO_INCREMENT PRIMARY KEY,
    poster_title VARCHAR(255) NOT NULL,
    cover_image_id INT NOT NULL,
    position ENUM('上边', '下边', '中间', '右边', '左边') NOT NULL,
    status ENUM('正常', '异常', '禁用') NOT NULL,
    created_by_user_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (cover_image_id) REFERENCES images(image_id),
    FOREIGN KEY (created_by_user_id) REFERENCES user(user_id)
);
*/
public class Posters {

    private int posterId;
    private String posterTitle;
    private int coverImageId;
    private String position;
    private String status;
    private int createdByUserId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public int getPosterId() {
        return posterId;
    }

    public void setPosterId(int posterId) {
        this.posterId = posterId;
    }

    public String getPosterTitle() {
        return posterTitle;
    }

    public void setPosterTitle(String posterTitle) {
        this.posterTitle = posterTitle;
    }

    public int getCoverImageId() {
        return coverImageId;
    }

    public void setCoverImageId(int coverImageId) {
        this.coverImageId = coverImageId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(int createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

}