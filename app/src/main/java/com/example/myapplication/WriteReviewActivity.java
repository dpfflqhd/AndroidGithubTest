package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import com.google.firebase.firestore.util.Listener;

import java.net.URI;

public class WriteReviewActivity extends AppCompatActivity {

    Button btn_writeFinish;
    EditText et_reviewText;
    RatingBar rb_reviewRating;
    Float starScore;
    ImageView iv_reviewImageInsert;
    int GET_GALLERY_IMAGE = 100;
    Uri selectedImageUri;


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
                    String imageUri = selectedImageUri.toString();
                    Log.d("이미지Uri", imageUri);
                }
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
        }
    }

}