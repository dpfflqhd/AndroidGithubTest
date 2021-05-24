package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class MyMusicActivity extends AppCompatActivity {

    MyMusicAdapter adapter;
    ArrayList<MyMusicVO> data;
    ListView lv_myMusic;
    ImageView iv_myMusic_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_music);

        // 요소 초기화
        lv_myMusic = findViewById(R.id.lv_myMusic);
        iv_myMusic_back = findViewById(R.id.iv_myMusic_back);
        data = new ArrayList<MyMusicVO>();

        iv_myMusic_back.setImageResource(R.drawable.next1);
        
        // 임의로 데이터셋 생성함. 여기에 DB를 받아오는 코드 필요
        for (int a=0; a<5; a++) {
            data.add(new MyMusicVO(R.drawable.zambalaya, "https://jll.ijjiii.is/ef31c690634901ebf0d3a2f38bf48a0e/HHQXdILvsPE/eecvcnovoavuu", "AI 음악 "+a+"번째"));
            if (a==3) {
                data.add(new MyMusicVO(R.drawable.salmon, "https://www.youtube.com/watch?v=HHQXdILvsPE", "AI 음악 "+a+"번째"));
            }
        }

        adapter = new MyMusicAdapter(getApplicationContext(), R.layout.adapter_my_music, data);

        // 어댑터 실행
        lv_myMusic.setAdapter(adapter);
    }


}