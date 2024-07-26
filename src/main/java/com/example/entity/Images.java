/*
CREATE TABLE `images` (
  `image_id` INT AUTO_INCREMENT PRIMARY KEY,
  `image_url` VARCHAR(255) NOT NULL,
  `image_name` VARCHAR(255) NOT NULL,
  `image_size` INT NOT NULL,
  `dimensions` VARCHAR(50),
  `image_status` BOOLEAN NOT NULL,
  `image_type` ENUM('png', 'jpg') NOT NULL,
  `upload_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `uploader_id` INT DEFAULT NULL,
  FOREIGN KEY (`uploader_id`) REFERENCES `user`(`user_id`) ON DELETE SET NULL
);
*/

package com.example.entity;

import java.sql.Timestamp;

public class Images {
    private int imageId;
    private String imageUrl;
    private String imageName;
    private int imageSize;
    private String dimensions;
    private boolean imageStatus;
    private String imageType;
    private Timestamp uploadTime;
    private Integer uploaderId; // Use Integer to allow null for foreign keys

    public Images() {
    }

    public Images(int imageId, String imageUrl, String imageName, int imageSize, String dimensions, boolean imageStatus, String imageType, Timestamp uploadTime, int uploaderId) {
        this.imageId = imageId;
        this.imageUrl = imageUrl;
        this.imageName = imageName;
        this.imageSize = imageSize;
        this.dimensions = dimensions;
        this.imageStatus = imageStatus;
        this.imageType = imageType;
        this.uploadTime = uploadTime;
        this.uploaderId = uploaderId;
    }

    // Getters and Setters
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getImageSize() {
        return imageSize;
    }

    public void setImageSize(int imageSize) {
        this.imageSize = imageSize;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public boolean getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(boolean imageStatus) {
        this.imageStatus = imageStatus;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(Integer uploaderId) {
        this.uploaderId = uploaderId;
    }

}
