package com.example.myapplication;

public class ReviewVO {

    int profileImage;
    String userId;
    String userName;
    String reviewText;
    String writeDate;
    String starPoint;
    String reviewImage;


    public ReviewVO(int profileImage, String userId, String userName, String reviewText, String writeDate, String starPoint, String reviewImage) {
        this.profileImage = profileImage;
        this.userId = userId;
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getStarPoint() {
        return starPoint;
    }

    public void setStarPoint(String starPoint) {
        this.starPoint = starPoint;
    }

    public String getReviewImage() {
        return reviewImage;
    }

    public void setReviewImage(String reviewImage) {
        this.reviewImage = reviewImage;
    }
}
