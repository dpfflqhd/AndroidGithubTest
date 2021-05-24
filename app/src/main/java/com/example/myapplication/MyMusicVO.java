package com.example.myapplication;

public class MyMusicVO {

    private int musicImg;
    private String musicId;
    private String musicName;

    public MyMusicVO(int musicImg, String musicId, String musicName) {
        this.musicImg = musicImg;
        this.musicName = musicName;
        this.musicId = musicId;
    }

    public int getMusicImg() {
        return musicImg;
    }

    public void setMusicImg(int musicImg) {
        this.musicImg = musicImg;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }
}
