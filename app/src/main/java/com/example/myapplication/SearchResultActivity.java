package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchResultActivity extends AppCompatActivity {

    ImageView iv_srchResultImage,
              iv_srchResultRelatedImage1,
              iv_srchResultRelatedImage2,
              iv_srchResultRelatedImage3,
              iv_srchResultImage3,
              iv_music,
              srch_back;

    Button btn_foodLike, btn_foodsave, btn_review;
    Button btn_music1, btn_music2, btn_music3, btn_music4, btn_music5, btn_music6, btn_music7, btn_music8, btn_music9, btn_music10, btn_music11, btn_music12, btn_music13,
            btn_music14, btn_music15, btn_music16, btn_music17, btn_music18, btn_music19, btn_music20, btn_music21, btn_music22, btn_music23, btn_music24, btn_music25;

    TextView tv_howMuchLikes,
            tv_srchResultFoodName,
            tv_srchResultFoodPrice,
            tv_srchResultResName,
            tv_srchResultResAddress,
            tv_srchResultResHour,
            tv_srchResultResTel,
            tv_srchResultRelatedImage1,
            tv_srchResultRelatedImage2,
            tv_srchResultRelatedImage3,
            tv_acc,
            tv_acc2;

    String user_name = "";

    String store = "";
    String email = "";
    String food = "";
    String name = "";
    String menu = "";
    String price = "";
    String phone = "";
    String addr = "";
    String time = "";
    String img = "";
    String audio = "";
    String foodsave_id = "";
    String foodsave_name = "";
    String sameStore1 = "";
    String sameStore2 = "";
    String sameStore3 = "";
    String sameStoreData1 = "";
    String sameStoreData2 = "";
    String sameStoreData3 = "";

    int real_like = 0;
    int cnt = 0;
    int like = 0;
    int save_cnt = 0;
    String like2 = "";
    String acc = "";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseFirestore foodsave_db = FirebaseFirestore.getInstance();

    MediaPlayer player;
    int position = 0; // ?????? ?????? ????????? ?????? ?????? ?????? ?????? ?????? ??????
    public static String url;


    ArrayList<LikeVO> data;
    int data1 = 0;

    public static String url1 = "/storage/emulated/0/Download/warning.wav";
    MediaPlayer player1;
    int position1 = 0; // ?????? ?????? ????????? ?????? ?????? ?????? ?????? ?????? ??????

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        data = new ArrayList<>();

        Intent read_intent = getIntent();
        store = read_intent.getStringExtra("data");
        email = read_intent.getStringExtra("email");
        acc = read_intent.getStringExtra("acc");
        sameStore1 = read_intent.getStringExtra("sameStore1");
        sameStore2 = read_intent.getStringExtra("sameStore2");
        sameStore3 = read_intent.getStringExtra("sameStore3");

        audio = read_intent.getStringExtra("audio");
        user_name = read_intent.getStringExtra("name");
        Log.d("result????????????, ????????? ????????? : ", store + "/" + email + "/" + acc + "/" + user_name);
//        Log.d("result????????????, ????????? ????????? : ", store + "/" + email + "/" + acc + "/" + user_name + "/" + sameStore1 + "/" + sameStore2 + "/" + sameStore3);
        Log.d("searchresult audio", audio);
        url = "/storage/emulated/0/Download/"+ audio +".mp3";


        // ????????? ??????????????????.
        btn_foodLike = findViewById(R.id.btn_foodLike);
        btn_foodsave = findViewById(R.id.btn_foodsave);
        btn_review = findViewById(R.id.btn_review2);


        iv_srchResultImage = findViewById(R.id.iv_srchResultImage);
        iv_srchResultRelatedImage1 = findViewById(R.id.iv_srchResultRelatedImage1);
        iv_srchResultRelatedImage2 = findViewById(R.id.iv_srchResultRelatedImage2);
        iv_srchResultRelatedImage3 = findViewById(R.id.iv_srchResultRelatedImage3);
        iv_srchResultImage3 = findViewById(R.id.iv_srchResultImage3);
        iv_music = findViewById(R.id.img_music);
        iv_music.setImageResource(R.drawable.ic_launcher_music_foreground);
        srch_back = findViewById(R.id.srch_back);


        btn_music1 = findViewById(R.id.music_1);
        btn_music2 = findViewById(R.id.music_2);
        btn_music3 = findViewById(R.id.music_3);
        btn_music4 = findViewById(R.id.music_4);
        btn_music5 = findViewById(R.id.music_5);
        btn_music6 = findViewById(R.id.music_6);
        btn_music7 = findViewById(R.id.music_7);
        btn_music8 = findViewById(R.id.music_8);
        btn_music9 = findViewById(R.id.music_9);
        btn_music10 = findViewById(R.id.music_10);
        btn_music11 = findViewById(R.id.music_11);
        btn_music12 = findViewById(R.id.music_12);
        btn_music13 = findViewById(R.id.music_13);
        btn_music14 = findViewById(R.id.music_14);
        btn_music15 = findViewById(R.id.music_15);
        btn_music16 = findViewById(R.id.music_16);
        btn_music17 = findViewById(R.id.music_17);
        btn_music18 = findViewById(R.id.music_18);
        btn_music19 = findViewById(R.id.music_19);
        btn_music20 = findViewById(R.id.music_20);
        btn_music21 = findViewById(R.id.music_21);
        btn_music22 = findViewById(R.id.music_22);
        btn_music23 = findViewById(R.id.music_23);
        btn_music24 = findViewById(R.id.music_24);
        btn_music25 = findViewById(R.id.music_25);

//        btn_music1.setEnabled(false);
        /*btn_music2.setEnabled(false);
        btn_music3.setEnabled(false);
        btn_music4.setEnabled(false);
        btn_music5.setEnabled(false);
        btn_music6.setEnabled(false);
        btn_music7.setEnabled(false);
        btn_music8.setEnabled(false);
        btn_music9.setEnabled(false);
        btn_music10.setEnabled(false);
        btn_music11.setEnabled(false);
        btn_music12.setEnabled(false);
        btn_music13.setEnabled(false);
        btn_music14.setEnabled(false);
        btn_music15.setEnabled(false);
        btn_music16.setEnabled(false);
        btn_music17.setEnabled(false);
        btn_music18.setEnabled(false);
        btn_music19.setEnabled(false);
        btn_music20.setEnabled(false);
        btn_music21.setEnabled(false);
        btn_music22.setEnabled(false);
        btn_music23.setEnabled(false);
        btn_music24.setEnabled(false);
        btn_music25.setEnabled(false);*/


        srch_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // ?????? ????????? ?????? ?????? ????????? ????????????
        tv_howMuchLikes = findViewById(R.id.tv_howMuchLikes);
        tv_srchResultFoodName = findViewById(R.id.tv_srchResultFoodName);
        tv_srchResultFoodPrice = findViewById(R.id.tv_srchResultFoodPrice);
        tv_srchResultResName = findViewById(R.id.tv_srchResultResName);
        tv_srchResultResAddress = findViewById(R.id.tv_srchResultResAddress);
        tv_srchResultResHour = findViewById(R.id.tv_srchResultResHour);
        tv_srchResultResTel = findViewById(R.id.tv_srchResultResTel);
        tv_srchResultRelatedImage1 = findViewById(R.id.tv_srchResultRelatedImage1);
        tv_srchResultRelatedImage2 = findViewById(R.id.tv_srchResultRelatedImage2);
        tv_srchResultRelatedImage3 = findViewById(R.id.tv_srchResultRelatedImage3);
        tv_acc = findViewById(R.id.tv_acc);
        tv_acc2 = findViewById(R.id.tv_acc2);

        iv_srchResultRelatedImage1.setImageResource(R.drawable.kum);
        iv_srchResultRelatedImage2.setImageResource(R.drawable.sinbul);
        iv_srchResultRelatedImage3.setImageResource(R.drawable.zerk);

        tv_srchResultRelatedImage1.setText("?????????????????? ?????????");
        tv_srchResultRelatedImage2.setText("?????????????????????");
        tv_srchResultRelatedImage3.setText("????????????");



        //????????? ??????

        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent review_intent = new Intent(getApplicationContext(), ReviewActivity.class);
                review_intent.putExtra("id", email);
                review_intent.putExtra("store", store);
                review_intent.putExtra("name", user_name);
                Log.d("searchresult name", "?" + user_name);
                startActivity(review_intent);
            }
        });

        /*btn_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.d("playAudio ??????", "yes");
                playAudio();
            }
        });*/

        //?????? ???????????? ???????????? ?????? ?????????
        Log.d("??? ??????", "");
        foodsave_db.collection("foodsave")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d("????????? ??????????????? ??????????????? :", document.getId());
                                foodsave_id = document.getString("id");
                                foodsave_name = document.getString("foodsave");
                                Log.d("??? ????????? :", foodsave_id + "/" + foodsave_name);
                                Log.d("?????? ????????? ??????", email + "/" + store);

                                if(email.equals(foodsave_id) && store.equals(foodsave_name)){
                                    btn_foodsave.setBackgroundResource(R.drawable.tagcolor2);
                                    save_cnt++;
                                } else {
                                    Log.d("??? ?????? ??????", "");
                                }
                            }

                        } else {
                            Log.w("??????????????????", "Error getting documents.", task.getException());

                        }
                    }
                });

        db.collection("store")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                    Log.d("??????????????? :", document.getId());
                                    name = document.getString("name");
                                    menu = document.getString("menu");
                                    price = document.getString("price");
                                    phone = document.getString("phone");
                                    addr = document.getString("addr");
                                    time = document.getString("time");
                                    like2 = document.getString("like");
                                    if (like2 != null) {
                                        try {
                                            like = Integer.parseInt(like2);
                                        } catch (NumberFormatException e) {
                                            // Deal with the situation like
                                            like = 0;
                                        }
                                    }

                                    if(store.equals("sakana")){
                                        iv_srchResultImage3.setImageResource(R.drawable.sakana1);

                                        btn_music1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();

                                            }
                                        });

                                        btn_music2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music3.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music4.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music5.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music6.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music7.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music8.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music9.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music10.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music11.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music12.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music13.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio();
                                            }
                                        });

                                        btn_music14.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music15.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music16.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music17.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music18.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music19.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music20.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music21.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music22.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music23.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music24.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music25.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });
                                    }

                                    if(store.equals("burger")){
                                        iv_srchResultImage3.setImageResource(R.drawable.burger1);
                                        btn_music1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();

                                            }
                                        });

                                        btn_music2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music3.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music4.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music5.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music6.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music7.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music8.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music9.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music10.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music11.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music12.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music13.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music14.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music15.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music16.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music17.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music18.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music19.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music20.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music21.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio();
                                            }
                                        });

                                        btn_music22.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music23.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music24.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music25.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });
                                    }

                                    if(store.equals("coco")){
                                        iv_srchResultImage3.setImageResource(R.drawable.coco1);

                                        btn_music1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();

                                            }
                                        });

                                        btn_music2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music3.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music4.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music5.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music6.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music7.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music8.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music9.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music10.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music11.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music12.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music13.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music14.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music15.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music16.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio();
                                            }
                                        });

                                        btn_music17.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music18.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music19.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music20.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music21.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music22.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music23.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music24.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });

                                        btn_music25.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(SearchResultActivity.this, "???????????????!", Toast.LENGTH_SHORT).show();
                                                playAudio1();
                                            }
                                        });
                                    }

                                    img = document.getString("img");

                                    //??????????????????????????? ????????? ?????????(???????????????)??? ???????????? ?????????????????? ????????????
                                    if (store.equals(document.getId())) {
                                        real_like = like;

                                        tv_srchResultFoodName.setText("?????? : " + menu);
                                        Log.d("tv_result", ""+ menu);
                                        tv_srchResultFoodPrice.setText("?????? : " + price);
                                        tv_srchResultResAddress.setText("?????? : " + addr);
                                        tv_srchResultResHour.setText("???????????? : " + time);
//                                        tv_srchResultResHour.setText("???????????? : ?????? 11:30 - 20:30");
                                        tv_srchResultResTel.setText("???????????? : " + phone);
                                        tv_srchResultResName.setText( name);
                                        Log.d("????????? :", store + "/ ?????? : " + name + "/?????? :" + menu + "/?????? :" + price + "/???????????? :" + phone + "/?????? :" + addr + "/???????????? :" +
                                                time + "/????????? ??? :" + like + "/?????????url :" + img);

                                        tv_howMuchLikes.setText("????????? " + String.valueOf(like) + "???" );
                                        tv_acc.setText(name + " ???????????? "+ menu + "???(???) ");
                                        String strNumber = String.format("%.0f", Float.parseFloat(acc) * 100);
                                        tv_acc2.setText(strNumber + "% ???????????????.");


//                                        tv_acc2.setText("99% ???????????????.");

                                        String image_url = img;
                                        Log.d("????????? img url :", img);
                                        Glide.with(SearchResultActivity.this).load(image_url).into(iv_srchResultImage);
                                    }


                                    //????????? ????????? ??????
                                    if(sameStore1.equals(document.getId())){

                                        tv_srchResultRelatedImage1.setText(name);
                                        String image_url = img;
                                        Log.d("same1 img url :", img);
                                        Glide.with(SearchResultActivity.this).load(image_url).into(iv_srchResultRelatedImage1);
                                    }

                                    if(sameStore2.equals(document.getId())){

                                        tv_srchResultRelatedImage2.setText(name);
                                        String image_url = img;
                                        Log.d("same2 img url :", img);
                                        Glide.with(SearchResultActivity.this).load(image_url).into(iv_srchResultRelatedImage2);

                                    }

                                    if(sameStore3.equals(document.getId())){

                                        tv_srchResultRelatedImage3.setText(name);
                                        String image_url = img;
                                        Log.d("same3 img url :", img);
                                        Glide.with(SearchResultActivity.this).load(image_url).into(iv_srchResultRelatedImage3);

                                    }

                                    Log.d("???????????? ??????1", "");
                                }

                        } else {
                            Log.w("??????????????????", "Error getting documents.", task.getException());
                        }
                    }
                });

        String db_url = "https://finfooproject-default-rtdb.firebaseio.com/";
        // ?????????????????? DB??? ???????????? ??????(=Connection)
        FirebaseDatabase database = FirebaseDatabase.getInstance(db_url);
        //?????????????????? DB??? ????????? ?????? ????????? ???????????? ??????
        DatabaseReference myRef = database.getReference(store);

        btn_foodLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //??????????????? ????????????
                if (btn_foodLike.isSelected()) {
                    btn_foodLike.setSelected(false);

                    real_like--;

                    tv_howMuchLikes.setText("????????? " + String.valueOf(real_like) + "???");

                    DocumentReference washingtonRef = db.collection("store").document(store);

                    //????????? ??? ???????????? (??????????????????)
                    washingtonRef
                            .update("like", String.valueOf(real_like))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("????????? ??? ??????", "like- successfully updated!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("????????? ??????", "Error updating document", e);
                                }
                            });
                } else {
                    //????????? ??????????????? (??????)
                    btn_foodLike.setSelected(true);

                    //????????? ??? ??????
                    real_like++;
                    String tv_like_update = String.valueOf(real_like);

                    //00 ?????? ???????????????. ????????? ??????
                    tv_howMuchLikes.setText("????????? " + tv_like_update + "???" );

                    DocumentReference washingtonRef = db.collection("store").document(store);

                    // Set the "isCapital" field of the city 'DC'
                    washingtonRef
                            .update("like", String.valueOf(real_like))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("????????? ??? ??????", "like+ successfully updated!");
                                    Log.d("????????? ??? ????", ""+real_like);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("????????? ??????", "Error updating document", e);
                                }
                            });
                }
            }
        });


        btn_foodsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(save_cnt < 1){
                    Map<String, Object> foodsave_map = new HashMap<>();
                    foodsave_map.put("id", email);
                    foodsave_map.put("foodsave", store);
                    Log.d("??? ??????", store + "/" + email);

                    db.collection("foodsave")
                            .add(foodsave_map)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("??? ?????? ?????? ??????:", "DocumentSnapshot added with ID: " + documentReference.getId());
                                    btn_foodsave.setBackgroundResource(R.drawable.tagcolor2);
                                    save_cnt++;
                                    Log.d("savecnt :", "" + save_cnt);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("??? ?????? ?????? ??????:", "Error adding document", e);
                                }
                            });
                }

                if(save_cnt < 1) {
                    foodsave_db.collection("foodsave")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {

                                            Log.d("????????? ??????????????? ??????????????? :", document.getId());
                                            foodsave_id = document.getString("id");
                                            foodsave_name = document.getString("foodsave");
                                            Log.d("??? ????????? :", foodsave_id + "/" + foodsave_name);
                                            Log.d("?????? ????????? ??????", email + "/" + store);

                                            if (email.equals(foodsave_id) && store.equals(foodsave_name)) {
                                            /*if(store.equals(foodsave_name)){
                                                Toast.makeText(getApplicationContext(), "?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                                                Log.d("?????? ????????? ???????????????.", "");
                                            }*/
                                                Toast.makeText(SearchResultActivity.this, "??? ????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                                                btn_foodsave.setBackgroundResource(R.drawable.tagcolor2);
                                                Log.d("??????", "/");
                                            } else{

                                            }
                                        }
                                    } else {
                                        Log.w("??????????????????", "Error getting documents.", task.getException());
                                    }
                                }
                            });
                } else {
                    Toast.makeText(SearchResultActivity.this, "?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    //?????? ??????
    private void playAudio() {
        try {
            closePlayer();

            Log.d("playAudio", url);
            player = new MediaPlayer();
            player.setDataSource(url);
            player.prepare();
            player.start();

            Thread myThread = new SearchResultActivity.TimerThread();
            myThread.start(); // start() -> run() ??? ??? ??????

            Toast.makeText(this, "?????? ??????!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void stopAudio() {
        if(player != null && player.isPlaying()){
            player.stop();

//            Toast.makeText(this, "?????????.", Toast.LENGTH_SHORT).show();
        }
    }

    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);


            int number = msg.arg1;
            /* TextView tv = (TextView)msg.obj;*/
//            tv_timer.setText(String.valueOf(number));
            if(number == 0){
                Toast.makeText(getApplicationContext(), "??? ?????? ??????????????? ??????????????? ??????!", Toast.LENGTH_LONG).show();
            }
        }
    };

    class TimerThread extends Thread{
        TextView tv;
        @Override
        public void run() {
            try {
                for(int i=10; i>=0; i--){
                    Thread.sleep(1000);

                    Message message = new Message();

                    message.arg1 = i;
                    /*message.obj = tv;*/


                    myHandler.sendMessage(message);
                }
                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // ??????????????? ?????? ??????
                        stopAudio();
//                        Toast.makeText(getApplicationContext(), "??????", Toast.LENGTH_SHORT).show();
                    }
                }, 0);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*public class loadImageTask extends AsyncTask<Bitmap, Void, Bitmap> {

        private String url;

        public loadImageTask(String url) {
            this.url = url;
        }

        @Override
        protected Bitmap doInBackground(Bitmap... params) {

            Bitmap imgBitmap = null;

            try {
                URL url1 = new URL(url);
                URLConnection conn = url1.openConnection();
                conn.connect();
                int nSize = conn.getContentLength();
                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(), nSize);
                imgBitmap = BitmapFactory.decodeStream(bis);
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return imgBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bit) {
            super.onPostExecute(bit);
            iv_srchResultImage.setImageBitmap(bit);
        }
    }*/

    private void playAudio1() {
        try {
            closePlayer1();

            player1 = new MediaPlayer();
            player1.setDataSource(url1);
            player1.prepare();
            player1.start();

            Thread myThread1 = new TimerThread1();
            myThread1.start(); // start() -> run() ??? ??? ??????

//            Toast.makeText(this, "?????? ?????????.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ?????? ??????????????? ???????????? ????????? ???????????? ????????? ??? ?????? ????????? ????????? ????????? ????????? ????????? ????????? ??????. (????????? ?????????.)
    private void pauseAudio1() {
        if (player1 != null) {
            position1 = player1.getCurrentPosition();
            player1.pause();

//            Toast.makeText(this, "???????????????.", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopAudio1() {
        if(player1 != null && player1.isPlaying()){
            player1.stop();

//            Toast.makeText(this, "?????????.", Toast.LENGTH_SHORT).show();
        }
    }

    /* ?????? ??? ????????? ????????? ??????. ???????????? lock ??????????????? ?????? ????????? ????????? ??? ??????.
     * ????????? ??? ???????????? ?????????????????????. */
    public void closePlayer1() {
        if (player1 != null) {
            player1.release();
            player1 = null;
        }
    }

    Handler myHandler1 = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);


            int number1 = msg.arg2;
            /* TextView tv = (TextView)msg.obj;*/
            if(number1 == 0){
//                Toast.makeText(getApplicationContext(), "????????????!", Toast.LENGTH_SHORT).show();
            }
        }
    };

    class TimerThread1 extends Thread{

        @Override
        public void run() {
            try {

                for(int i=10; i>=0; i--){
                    Thread.sleep(300);

                    Message message1 = new Message();

                    message1.arg2 = i;
                    /*message.obj = tv;*/


                    myHandler1.sendMessage(message1);
                }



                Handler mHandler1 = new Handler(Looper.getMainLooper());
                mHandler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // ??????????????? ?????? ??????
                        stopAudio1();
//                        Toast.makeText(getApplicationContext(), "?????????.", Toast.LENGTH_SHORT).show();
                    }
                }, 0);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}