package com.example.myapplication;

public class User {
    private String id;
    private String pw;
    private String name;
    private String age;
    private String sex;
    private String address;

    public User(){}

    public User(String id, String pw, String name, String age, String sex, String address) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.address = address;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
