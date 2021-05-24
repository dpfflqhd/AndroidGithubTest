package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    MyMusicAdapter adapter;
    ArrayList<MyMusicVO> data;
    ListView lv_myMusic;
    ImageView iv_myMusic_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);


    }
}