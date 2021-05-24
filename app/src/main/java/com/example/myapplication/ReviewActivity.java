package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    ReviewAdapter adapter;
    ArrayList<ReviewVO> data;
    ListView lv_reviewAllList;
    Button btn_writeReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // 요소 초기화
        lv_reviewAllList = findViewById(R.id.lv_reviewAllList);
        data = new ArrayList<ReviewVO>();
        btn_writeReview = findViewById(R.id.btn_writeReview);

        // 임의로 데이터셋 생성함. 여기에 DB를 받아오는 코드 필요
        // int profileImage, String userId, String reviewText, String writeDate, int starPoint, int reviewImage
        for (int a=0; a<5; a++) {
            data.add(new ReviewVO(R.drawable.paddington, "유저"+a, "정말 맛있어요"+a, "작성일 "+a+"월"+a+"일", a, R.drawable.salmon));
            if (a==3) {
                data.add(new ReviewVO(R.drawable.paddington, "유저"+a, "정말 맛있어요"+a, "작성일 "+a+"월"+a+"일", a, R.drawable.salmon));
            }
        }

        // 어댑터 실행
        adapter = new ReviewAdapter(getApplicationContext(), R.layout.adpater_review_list, data);
        lv_reviewAllList.setAdapter(adapter);

        // 리뷰작성 버튼 누를시 다른 페이지로 이동
        btn_writeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewActivity.this, WriteReviewActivity.class);
                startActivity(intent);
            }
        });

    }
}