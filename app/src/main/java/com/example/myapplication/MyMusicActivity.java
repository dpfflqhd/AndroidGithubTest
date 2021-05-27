package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class MyMusicActivity extends AppCompatActivity {

    MyMusicAdapter adapter;
    ArrayList<MyMusicVO> data;
    ListView lv_myMusic;
    ImageView iv_myMusic_back;
    String email ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_music);

        Intent read_intent = getIntent();
        email = read_intent.getStringExtra("id");
        Log.d("mymusic email", email);
        // 요소 초기화
        lv_myMusic = findViewById(R.id.lv_reviewAllList);
        iv_myMusic_back = findViewById(R.id.iv_myMusic_back);
        data = new ArrayList<MyMusicVO>();

        iv_myMusic_back.setImageResource(R.drawable.next1);

        //1. 이미지파일 목록을 불러올 경로를 구한다.
        String path = "/storage/emulated/0/Download/";
        //path에는 "sdcard/ImageList/" 와 같은 값이 들어갑니다.
        // 2. 경로를 이용해 File 객체를 생성한다.
        File list = new File(path);
        //3. list객체에서 이미지목록만 추려 낸다.

        String[] fileList = list.list(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                Boolean bOK = false;
                if(filename.toLowerCase().endsWith(".mp3")) bOK = true;
                if(filename.toLowerCase().endsWith(".mp4")) bOK = true;
                return bOK;
            }
        });

        List<String> userAudioList = new ArrayList<>();
        String test = "";
        int cnt = 0;

        String email_replace = email.replace("@gmail.com", "");

        for(int i = 0; i < fileList.length; i++){
            if(fileList[i].contains(email_replace)){
                test = fileList[i];
                Log.d("filelist", fileList[i]);
                if(cnt > 0){
                    cnt++;
                }
            }
            if(test != null){
                userAudioList.add("/storage/emulated/0/Download/"+ test);
            }
        }

        if(userAudioList != null && !userAudioList.isEmpty()){
            for (int i = 0; i < userAudioList.size(); i++) {
                data.add(new MyMusicVO(R.drawable.zambalaya, userAudioList.get(i), "AI 음악 "+(i+1)+"번째"));
                Log.d("userAudioList", "" + userAudioList.get(i) + "/" + i);
            }
        }
        //toLowerCase : 소문자로 변환
        // endsWith() : 끝의 문자가 ()안의 문자와 같은지 판별해서 Boolean형으로 리턴한다.
        // 파일목록중 png, 9.png, gif, jpg 확장자를 가진 파일들 목록만 imgList에 저장된다.
        // 임의로 데이터셋 생성함. 여기에 DB를 받아오는 코드 필요

        /*for (int a=0; a<5; a++) {
            data.add(new MyMusicVO(R.drawable.zambalaya, "/storage/emulated/0/Download//test20_19.mp3", "AI 음악 "+a+"번째"));
            if (a==3) {
                data.add(new MyMusicVO(R.drawable.salmon, "https://www.youtube.com/watch?v=HHQXdILvsPE", "AI 음악 "+a+"번째"));
            }
        }*/

        adapter = new MyMusicAdapter(getApplicationContext(), R.layout.adapter_my_music, data);

        // 어댑터 실행
        lv_myMusic.setAdapter(adapter);
    }


}