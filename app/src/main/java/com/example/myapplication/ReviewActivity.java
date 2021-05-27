package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReviewActivity extends AppCompatActivity {

    ReviewAdapter adapter;
    ArrayList<ReviewVO> data;
    ListView lv_reviewAllList;
    Button btn_writeReview;

    private String email = "";
    private String store = "";
    private String user_name = "";

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);


        // 요소 초기화
        lv_reviewAllList = findViewById(R.id.lv_reviewAllList);
        data = new ArrayList<ReviewVO>();
        btn_writeReview = findViewById(R.id.btn_writeReview);

        Intent read_intent = getIntent();
        email = read_intent.getStringExtra("id");
        store = read_intent.getStringExtra("store");
        user_name = read_intent.getStringExtra("name");
        Log.d("review receive name", user_name);


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
                Intent write_intent = new Intent(getApplicationContext(), WriteReviewActivity.class);
                write_intent.putExtra("id", email);
                write_intent.putExtra("store", store);
                write_intent.putExtra("name", user_name);
                Log.d("Review Act put intent", email + "/" + store + "/" + user_name);
                startActivity(write_intent);
            }
        });

    }
}