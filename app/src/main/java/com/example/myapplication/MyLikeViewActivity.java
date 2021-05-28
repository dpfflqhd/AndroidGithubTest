package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyLikeViewActivity extends AppCompatActivity {

    MyLikeViewAdapter adapter;
    ArrayList<LikeViewVO> data;
    ListView lv_like;
    ImageView iv_like_back;
    private String email;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String id, store;

    List<String> user_save_list = new ArrayList<>();
    List<String> user_img_list = new ArrayList<>();
    List<String> user_store_list = new ArrayList<>();
    List<String> user_menu_list = new ArrayList<>();
    List<String> user_rating_list = new ArrayList<>();


    int cnt = 0;

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
    String document_id = "";
    String store_name = "";
    String rating = "";
    int cnt_store = 0;
    String a= "";
    String b= "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_like_view2);

        Intent read_intent = getIntent();
        email = read_intent.getStringExtra("id");
        Log.d("first frag 받아온 데이터 : ", email);

        // 요소 초기화

        /*iv_dishImg 음식점사진
        tv_resName 음식점 이름
        tv_dishName 메뉴 이름
        tv_starScore 별점*/






        //저장한 음식점 받아오기
        db.collection("foodsave")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                    Log.d("문서리스트 :", document.getId());
                                id = document.getString("id");
                                store = document.getString("foodsave");
                                Log.d("foodsave document", document.getId());
                                Log.d("id, store", id + store);

                                //이미지액티비티에서 받아온 결과값(음식점이름)에 해당되는 데이터베이스 불러오기
                                if (email.equals(id)) {
                                    user_save_list.add(store);
                                    Log.d("user_save_list", store);
                                }
                                Log.d("받아오기 실패1", "");
                            }

                            if(user_save_list != null && !user_save_list.isEmpty()){
                                for(int i = 0; i < user_save_list.size(); i++){
                                    Log.d("user save list", user_save_list.get(i));
                                }
                            }

                            if(user_save_list != null && !user_save_list.isEmpty()){
                                db.collection("store")
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {

//                                    Log.d("문서리스트 :", document.getId());
                                                        name = document.getString("name");
                                                        menu = document.getString("menu");
                                                        price = document.getString("price");
                                                        phone = document.getString("phone");
                                                        addr = document.getString("addr");
                                                        time = document.getString("time");
                                                        img = document.getString("img");
                                                        rating = document.getString("rating");
                                                        store_name = document.getId();
                                                        document_id = document.getId();

                                                        //이미지액티비티에서 받아온 결과값(음식점이름)에 해당되는 데이터베이스 불러오기

                                                        for(int i = 0; i < user_save_list.size(); i++){
                                                            if (user_save_list.get(i).equals(document_id)){
                                                                user_img_list.add(img);
                                                                user_menu_list.add(menu);
                                                                user_store_list.add(name);
                                                                user_rating_list.add(rating);
                                                        }

//                                       Glide.with(SearchResultActivity.this).load(image_url).into(iv_srchResultImage);
                                                            Log.d("cnt_store", ""+cnt_store);

                                                        }
                                                        Log.d("받아오기 실패1", "");
                                                    }

                                                    if(user_img_list != null && !user_img_list.isEmpty()) {
                                                        for (int i = 0; i < user_img_list.size(); i++) {
                                                            Log.d("user save list", user_img_list.get(i));
                                                            Log.d("user menu list", user_menu_list.get(i));
                                                            Log.d("user store list", user_store_list.get(i));
                                                            Log.d("user rating list", user_rating_list.get(i));
                                                            cnt++;
                                                        }
                                                    }

                                                    if(cnt > 0){
                                                        lv_like = findViewById(R.id.lv_reviewAllList);
                                                        iv_like_back = findViewById(R.id.iv_like_back);
                                                        data = new ArrayList<LikeViewVO>();
                                                        iv_like_back.setImageResource(R.drawable.next1);

                                                        // 데이터 생성 부분. 일단 임시로 아무거나 만듬.
                                                        for (int i=0; i<user_img_list.size(); i++) {
                                                            data.add(new LikeViewVO(user_img_list.get(i),"장소"+i, user_store_list.get(i), user_menu_list.get(i), user_rating_list.get(i), "확인"));
                                                        }

                                                        adapter = new MyLikeViewAdapter(getApplicationContext(), R.layout.activity_my_like_view, data);
                                                        // 어댑터 실행
                                                        lv_like.setAdapter(adapter);

                                                        iv_like_back.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                finish();
                                                            }
                                                        });
                                                    }
                                                } else {
                                                    Log.w("받아오기실패", "Error getting documents.", task.getException());
                                                }
                                            }
                                        });
                            }
                        } else {
                            Log.w("받아오기실패", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public class ImageHelper {
        public Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                    .getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);
            final float roundPx = pixels;

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

            return output;
        }
    }
}