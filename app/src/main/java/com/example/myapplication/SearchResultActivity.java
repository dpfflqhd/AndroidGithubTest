package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
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
              iv_srchResultRelatedImage3;

    Button btn_foodLike, btn_foodsave;

    TextView tv_howMuchLikes,
            tv_srchResultFoodName,
            tv_srchResultFoodPrice,
            tv_srchResultResName,
            tv_srchResultResAddress,
            tv_srchResultResHour,
            tv_srchResultResTel,
            tv_srchResultRelatedImage1,
            tv_srchResultRelatedImage2,
            tv_srchResultRelatedImage3;

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
    String foodsave_id = "";
    String foodsave_name = "";
    int cnt = 0;
    int like = 0;
    int save_cnt = 0;
    String like2 = "";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseFirestore foodsave_db = FirebaseFirestore.getInstance();

    ArrayList<LikeVO> data;
    int data1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        data = new ArrayList<>();

        Intent read_intent = getIntent();
        store = read_intent.getStringExtra("data");
        email = read_intent.getStringExtra("email");
        Log.d("result액티비티, 받아온 데이터 : ", store);

        // 요소를 초기화합니다.
        btn_foodLike = findViewById(R.id.btn_foodLike);
        btn_foodsave = findViewById(R.id.btn_foodsave);

        iv_srchResultImage = findViewById(R.id.iv_srchResultImage);
        iv_srchResultRelatedImage1 = findViewById(R.id.iv_srchResultRelatedImage1);
        iv_srchResultRelatedImage2 = findViewById(R.id.iv_srchResultRelatedImage2);
        iv_srchResultRelatedImage3 = findViewById(R.id.iv_srchResultRelatedImage3);

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

        //이미 찜한거면 색깔칠한 하트 나오게
        Log.d("찜 클릭", "");
        foodsave_db.collection("foodsave")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d("찜목록 문서리스트 문서아이디 :", document.getId());
                                foodsave_id = document.getString("id");
                                foodsave_name = document.getString("foodsave");
                                Log.d("찜 리스트 :", foodsave_id + "/" + foodsave_name);
                                Log.d("현재 음식점 이름", email + "/" + store);

                                if(email.equals(foodsave_id) && store.equals(foodsave_name)){
                                    btn_foodsave.setBackgroundResource(R.drawable.heart2);
                                    save_cnt++;
                                }
                            }

                        } else {
                            Log.w("받아오기실패", "Error getting documents.", task.getException());

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

                                Log.d("문서리스트 :", document.getId());
                                name = document.getString("name");
                                menu = document.getString("menu");
                                price = document.getString("price");
                                phone = document.getString("phone");
                                addr = document.getString("addr");
                                time = document.getString("time");
                                like2 = document.getString("like");
                                like = Integer.parseInt(like2);
                                img = document.getString("img");

                                //결과 이미지뷰를 데이터베이스에서 가져온 url로 띄우기
                                /*String image_url = img;

                                Glide.with(this).load(image_url).into(iv_srchResultImage);*/
                                /*loadImageTask imageTask = new loadImageTask();
                                Log.d("가져온 url", img);
                                imageTask.execute();*/

                                tv_howMuchLikes.setText(String.valueOf(like) + "명이 좋아합니다");
                                tv_srchResultFoodName.setText(menu);
                                tv_srchResultFoodPrice.setText(price);
                                tv_srchResultResAddress.setText(addr);
                                tv_srchResultResHour.setText(time);
                                tv_srchResultResTel.setText(phone);
                                tv_srchResultResName.setText(name);


                                //이미지액티비티에서 받아온 결과값(음식점이름)에 해당되는 데이터베이스 불러오기
                                if(store.equals(document.getId())){
                                    Log.d("음식점 :", store + "/ 이름 : " + name + "/메뉴 :" + menu + "/가격 :" + price + "/전화번호 :" + phone + "/주소 :" + addr + "/운영시간 :" +
                                            time + "/좋아요 수 :" + like + "/이미지url :" + img);
                                    String image_url = img;
                                    Log.d("불러온 img url :" , img);

                                    Glide.with(SearchResultActivity.this).load(image_url).into(iv_srchResultImage);


                                }
//                                Log.d("음식점 :", store + "/ 이름 : " + name + "/메뉴 :" + menu + "/가격 :" + price + "/전화번호 :" + phone + "/주소 :" + addr + "/운영시간 :" + time );
//                                Log.d("받아온 데이터 :", document.getId() + " => " + food);
                                Log.d("받아오기 실패1", "");

                            }

                        } else {
                            Log.w("받아오기실패", "Error getting documents.", task.getException());

                        }



                    }
                });




        String db_url = "https://finfooproject-default-rtdb.firebaseio.com/";
        // 파이어베이스 DB에 접근하는 객체(=Connection)
        FirebaseDatabase database = FirebaseDatabase.getInstance(db_url);
        //파이어베이스 DB에 저장된 특정 경로를 참조하는 객체
        DatabaseReference myRef = database.getReference(store);

        btn_foodLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btn_foodLike.isSelected()) {
                    btn_foodLike.setSelected(false);

                    if(like > 0){
                        like--;
                    }

                    tv_howMuchLikes.setText(String.valueOf(like) + "명이 좋아합니다");

                    DocumentReference washingtonRef = db.collection("store").document(store);

                    // Set the "isCapital" field of the city 'DC'
                    washingtonRef
                            .update("like", String.valueOf(like))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("좋아요 수 감소", "DocumentSnapshot successfully updated!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("좋아요 실패", "Error updating document", e);
                                }
                            });

                } else {
                    btn_foodLike.setSelected(true);

                    //좋아요 수 증가
                    like++;

                    //00 명이 좋아합니다. 텍스트 변경
                    tv_howMuchLikes.setText(String.valueOf(like) + "명이 좋아합니다");

                    DocumentReference washingtonRef = db.collection("store").document(store);

                    // Set the "isCapital" field of the city 'DC'
                    washingtonRef
                            .update("like", String.valueOf(like))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("좋아요 수 증가", "DocumentSnapshot successfully updated!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("좋아요 실패", "Error updating document", e);
                                }
                            });










                    //파이어베이스DB에 저장된 내용을 읽어온 후 ArrayList<ChatVO>에 저장
                    myRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            //데이터가 추가되었을 때 실행되는 메소드
                            LikeVO vo = dataSnapshot.getValue(LikeVO.class);
                            data.add(vo);
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

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

                    Log.d("찜 확인", store + "/" + email);

                    // Add a new document with a generated ID

                    db.collection("foodsave")
                            .add(foodsave_map)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

//                                            document_id = documentReference.getId();
                                    Log.d("찜 목록 추가 성공:", "DocumentSnapshot added with ID: " + documentReference.getId());
                                    btn_foodsave.setBackgroundResource(R.drawable.heart2);
                                    save_cnt++;

                                    Log.d("savecnt :", "" + save_cnt);


                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("찜 목록 추가 실패:", "Error adding document", e);
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

                                            Log.d("찜목록 문서리스트 문서아이디 :", document.getId());
                                            foodsave_id = document.getString("id");
                                            foodsave_name = document.getString("foodsave");
                                            Log.d("찜 리스트 :", foodsave_id + "/" + foodsave_name);
                                            Log.d("현재 음식점 이름", email + "/" + store);

                                            if (email.equals(foodsave_id) && store.equals(foodsave_name)) {
                                            /*if(store.equals(foodsave_name)){
                                                Toast.makeText(getApplicationContext(), "이미 찜하신 음식점입니다.", Toast.LENGTH_SHORT).show();
                                                Log.d("이미 찜하신 목록입니다.", "");
                                            }*/
                                                Toast.makeText(SearchResultActivity.this, "이미 찜하신 음식점입니다.", Toast.LENGTH_SHORT).show();
                                                btn_foodsave.setBackgroundResource(R.drawable.heart2);
                                                Log.d("확인ㅇㅀ", "/");
                                            } else{


                                            }

                                            //이미지액티비티에서 받아온 결과값(음식점이름)에 해당되는 데이터베이스 불러오기
                                        /*if(foodsave_name.equals(store) && foodsave_id.equals(email)){
                                            Toast.makeText(getApplicationContext(), "이미 찜하신 음식점입니다.", Toast.LENGTH_SHORT).show();
                                            Log.d("이미 찜하신 목록입니다.", "");
                                        } else if(foodsave_id.equals(email)) {
                                            if (foodsave_id.equals(email) && !foodsave_name.equals(store)) {

                                                Map<String, Object> foodsave_map = new HashMap<>();

                                                foodsave_map.put("id", email);
                                                foodsave_map.put("foodsave", store);

                                                Log.d("찜 확인", store + "/" + email);

                                                // Add a new document with a generated ID
                                                db.collection("foodsave")
                                                        .add(foodsave_map)
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                            @Override
                                                            public void onSuccess(DocumentReference documentReference) {

//                                            document_id = documentReference.getId();
                                                                Log.d("찜 목록 추가 성공:", "DocumentSnapshot added with ID: " + documentReference.getId());

                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.w("찜 목록 추가 실패:", "Error adding document", e);
                                                            }
                                                        });
                                            }
                                        }*/

                                        }

                                    } else {
                                        Log.w("받아오기실패", "Error getting documents.", task.getException());

                                    }
                                }
                            });
                }





            }
        });
    }

    public class loadImageTask extends AsyncTask<Bitmap, Void, Bitmap> {

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
    }
}