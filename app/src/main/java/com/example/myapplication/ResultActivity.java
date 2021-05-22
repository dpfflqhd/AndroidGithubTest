package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private Button btn_after, btn_before, btn_like, btn_review;
    private TextView tv_count, tv_name, tv_menu, tv_price, tv_tell, tv_addr, tv_time;
    private ImageView img_food, img_reco1, img_reco2, img_reco3;
    String store = "coco";
    String food = "";
    String name = "";
    String menu = "";
    String price = "";
    String phone = "";
    String addr = "";
    String time = "";
    int cnt = 0;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<LikeVO> data;

    int data1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        data = new ArrayList<>();

        Intent read_intent = getIntent();
//        store = read_intent.getStringExtra("data");
//        Log.d("result액티비티, 받아온 데이터 : ", store);

        btn_after = findViewById(R.id.btn_after); btn_before = findViewById(R.id.btn_before);
        btn_like = findViewById(R.id.btn_like);   btn_review = findViewById(R.id.btn_review);

        tv_count = findViewById(R.id.tv_count);   tv_name = findViewById(R.id.tv_name);
        tv_menu = findViewById(R.id.tv_menu);     tv_price = findViewById(R.id.tv_price);
        tv_tell = findViewById(R.id.tv_tell);     tv_addr = findViewById(R.id.tv_addr);
        tv_time = findViewById(R.id.tv_time);

        img_food = findViewById(R.id.img_food);   img_reco1 = findViewById(R.id.img_reco1);
        img_reco2 = findViewById(R.id.img_reco2); img_reco3 = findViewById(R.id.img_reco3);

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



                                //이미지액티비티에서 받아온 결과값(음식점이름)에 해당되는 데이터베이스 불러오기
                                if(store.equals(document.getId())){
                                    Log.d("음식점 :", store + "/ 이름 : " + name + "/메뉴 :" + menu + "/가격 :" + price + "/전화번호 :" + phone + "/주소 :" + addr + "/운영시간 :" + time );

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

        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btn_like.isSelected()){
                    btn_like.setSelected(false);
                    tv_count.setText("눌렀음");
                } else {
                    btn_like.setSelected(true);
                    tv_count.setText("안눌렀음");

                     //push() = 입력한 데이터를 계속 쌓이게 해줌, 랜덤한 key값을 생성하는 메소드
                    if(cnt == 0 ){
                        myRef.push().setValue(new LikeVO(cnt));
                    } else {

                    }



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
}


    /*//플라스크에서 결과 이미지url 받아오기
    private JSONObject buidJsonObject(JSONObject data) throws JSONException {

        JSONObject jsonObject = new JSONObject();
        Bitmap bitmap =((BitmapDrawable)img_food.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();
        String img_array = Base64.encodeToString(imageInByte, Base64.DEFAULT);
        String a = data.getString("process_image");
        Log.d("플라스크에서 받아온 이미지 url : ", a);
        // String img_array = new String(imageInByte);
        try {
            baos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        jsonObject.accumulate("image_Array",img_array);

        return jsonObject;
    }*/
}