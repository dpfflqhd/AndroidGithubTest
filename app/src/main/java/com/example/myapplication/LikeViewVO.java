package com.example.myapplication;

public class LikeViewVO {

    private String dishImg;
    private String resLoc;
    private String resName;
    private String dishName;
    private String starScore;
    private String memo;

    public LikeViewVO(String dishImg, String resLoc, String resName, String dishName, String starScore, String memo) {
        this.dishImg = dishImg;
        this.resLoc = resLoc;
        this.resName = resName;
        this.dishName = dishName;
        this.starScore = starScore;
        this.memo = memo;
    }

    public String getResLoc() {
        return resLoc;
    }

    public void setResLoc(String resLoc) {
        this.resLoc = resLoc;
    }

    public String getStarScore() {
        return starScore;
    }

    public void setStarScore(String starScore) {
        this.starScore = starScore;
    }

    public String getDishImg() {
        return dishImg;
    }

    public void setDishImg(String dishImg) {
        this.dishImg = dishImg;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
