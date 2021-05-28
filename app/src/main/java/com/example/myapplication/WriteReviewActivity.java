package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.util.Listener;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WriteReviewActivity extends AppCompatActivity {

    Button btn_writeFinish;
    EditText et_reviewText;
    RatingBar rb_reviewRating;
    Float starScore;
    ImageView iv_reviewImageInsert;
    int GET_GALLERY_IMAGE = 100;
    Uri selectedImageUri;
    private File tempFile;

    private String email;
    private String user_name;
    private String store_name;
    String document_id;
    private String imageUri;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    long now = System.currentTimeMillis();
    Date mDate = new Date(now);
    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
    String getTime = simpleDate.format(mDate);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        // 요소를 초기화
        btn_writeFinish = findViewById(R.id.btn_writeFinish);
        et_reviewText = findViewById(R.id.et_reviewText);
        rb_reviewRating = findViewById(R.id.rb_reviewRating);
        iv_reviewImageInsert = findViewById(R.id.iv_reviewImageInsert);

        // 별점 기본값 3.0
        rb_reviewRating.setRating(3.0f);
        starScore = 3.0f;

        //ReviewActivity에서 값 받아오기
        Intent read_intent = getIntent();
        email = read_intent.getStringExtra("id");
        store_name = read_intent.getStringExtra("store");
        user_name = read_intent.getStringExtra("name");
        Log.d("write user get intent",email + "/" + store_name + "/" + user_name);


        // 별점이 변경되었을때 이를 추적하는 리스너
        rb_reviewRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                starScore = rating;
            }
        });

        // 완료버튼을 눌렀을때
        btn_writeFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 리뷰 데이터를 받아서 저장
                String reviewText = et_reviewText.getText().toString();
                Log.d("리뷰텍스트", reviewText);
                
                // 이미지 Uri를 받아서 저장
                // 이미지를 넣지 않고 리뷰 작성을 하는경우가 있을 수 있으므로 if로 분기
                if (selectedImageUri != null) {
                    imageUri = selectedImageUri.toString();
                    Log.d("이미지Uri", imageUri);
                }

                //파이어베이스에 리뷰 데이터 저장
                Map<String, Object> review_map = new HashMap<>();

                review_map.put("store", store_name);
                review_map.put("id", email);
                review_map.put("name", user_name);
                review_map.put("review", reviewText);
                review_map.put("rating", String.valueOf(starScore));
                if(tempFile != null) {
                    review_map.put("img", tempFile.toString());
                } else {
                    review_map.put("img", "a");

                }
                review_map.put("time", getTime);

                Log.d("review 데이터베이스 저장", store_name + "/" + email + "/" + user_name + "/" + reviewText + "/" + starScore + "/" + "/" + getTime);

                //파이베이스 imageurl 컬렉션에 데이터 넣기
                db.collection("reviews")
                        .add(review_map)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                                document_id = documentReference.getId();
                                Log.d("review 데이터베이스 추가:", "DocumentSnapshot added: " + documentReference.getId());


                                Intent back_intent = new Intent(getApplicationContext(), ReviewActivity.class);
                                back_intent.putExtra("id", email);
                                back_intent.putExtra("store", store_name);
                                back_intent.putExtra("name", user_name);
                                startActivity(back_intent);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("데이터베이스 추가실패:", "Error adding document", e);
                            }
                        });
            }
        });

        // 사진 첨부 버튼을 눌렀을때.
        // 갤러리에 접근한 다음 사용자가 선택한 이미지를 URI로 저장하고, 인텐트로 넘겨준다.
        iv_reviewImageInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

    }

    // 인텐트 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        // 갤러리에 접근해서 가져온 이미지를 이미지뷰에 넣는 코드
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            iv_reviewImageInsert.setImageURI(selectedImageUri);


            Uri photoUri = data.getData();

            Cursor cursor = null;
            try {
                String[] proj = { MediaStore.Images.Media.DATA };

                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            Log.d("selectedUri", tempFile.toString());



        }
    }

}