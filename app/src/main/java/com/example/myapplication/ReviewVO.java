package com.example.myapplication;

public class ReviewVO {

    int profileImage;
    String userId;
    String reviewText;
    String writeDate;
    int starPoint;
    int reviewImage;

    public ReviewVO(int profileImage, String userId, String reviewText, String writeDate, int starPoint, int reviewImage) {
        this.profileImage = profileImage;
        this.userId = userId;
        this.reviewText = reviewText;
        this.writeDate = writeDate;
        this.starPoint = starPoint;
        this.reviewImage = reviewImage;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

    public int getStarPoint() {
        return starPoint;
    }

    public void setStarPoint(int starPoint) {
        this.starPoint = starPoint;
    }

    public int getReviewImage() {
        return reviewImage;
    }

    public void setReviewImage(int reviewImage) {
        this.reviewImage = reviewImage;
    }
}
