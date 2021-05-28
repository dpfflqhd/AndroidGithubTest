package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.List;
import java.util.Map;

public class ReviewActivity extends AppCompatActivity {

    ReviewAdapter adapter;
    ArrayList<ReviewVO> data;
    ListView lv_reviewAllList;
    Button btn_writeReview;
    ImageView imageView17;

    private String email = "";
    private String store = "";
    private String user_name = "";
    private String real_email = "";
    private String real_store = "";
    private String real_user_name = "";
    private int cnt;
    String review, img, time, rating, getstore;
    List<ReviewVO> review_data_map = new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);


        // 요소 초기화
        lv_reviewAllList = findViewById(R.id.lv_reviewAllList);
        data = new ArrayList<ReviewVO>();
        btn_writeReview = findViewById(R.id.btn_writeReview);
        imageView17 = findViewById(R.id.imageView17);

        Intent read_intent = getIntent();
        email = read_intent.getStringExtra("id");
        store = read_intent.getStringExtra("store");
        user_name = read_intent.getStringExtra("name");
        Log.d("review receive name", user_name);

        imageView17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        db.collection("reviews").orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {



                                    real_store = document.getString("store");
                                    real_user_name = document.getString("name");
                                    real_email = document.getString("id");
                                    review = document.getString("review");
                                    img = document.getString("img");
                                    time = document.getString("time");
                                    rating = document.getString("rating");


                                    cnt++;
                                if(store.equals(real_store)){
                                    // 임의로 데이터셋 생성함. 여기에 DB를 받아오는 코드 필요
                                    // int profileImage, String userId, String reviewText, String writeDate, int starPoint, int reviewImage
                                    review_data_map.add(new ReviewVO(R.drawable.profile, real_email, real_user_name, review, time, rating, img));
                                    Log.d("review data map", real_email + "/" + real_user_name + "/" + review + "/" + time + "/" + rating + "/" + img);
                                }
                                Log.d("받아오기 실패1", "");
                            }
                            Log.d("cnt", ""+cnt);
                            data.clear();

                            if(review_data_map != null && !review_data_map.isEmpty()){
                                for (int a=0; a < cnt; a++) {
                                    Log.d("review data size", ""+review_data_map.size());

                                    data.add(new ReviewVO(review_data_map.get(a).getProfileImage(), review_data_map.get(a).getUserId(), review_data_map.get(a).getUserName(), review_data_map.get(a).getReviewText(), review_data_map.get(a).getWriteDate(), review_data_map.get(a).starPoint, review_data_map.get(a).getReviewImage()));
                                    /*if (a==3) {
                                        data.add(new ReviewVO(review_data_map.get(a).getProfileImage(), review_data_map.get(a).getUserId(), review_data_map.get(a).getReviewText(), review_data_map.get(a).getWriteDate(), review_data_map.get(a).starPoint, review_data_map.get(a).getReviewImage()));
                                    }*/
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



                        } else {
                            Log.w("받아오기실패", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}