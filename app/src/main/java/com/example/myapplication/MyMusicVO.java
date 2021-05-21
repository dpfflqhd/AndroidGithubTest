package com.example.myapplication;

public class MyMusicVO {

    private int musicImg;
    private int musicId;
    private String musicName;

    public MyMusicVO(int musicImg, int musicId, String musicName) {
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

    public int getMusicId() {
        return musicId;
    }

    public void setMusicId(int musicId) {
        this.musicId = musicId;
    }
}
