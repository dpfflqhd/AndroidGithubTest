package com.example.myapplication;

import android.util.Log;

public class LikeSortVO{
    private String name ="";
    private String menu ="";
    private String img ="";
    private String rating ="";
    private int like;
    private String store_name = "";


    public LikeSortVO(String name, String menu, String img, String rating, int like, String store_name) {
        this.name = name;
        this.menu = menu;
        this.img = img;
        this.rating = rating;
        this.like = like;
        this.store_name = store_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

}
