package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchResultActivity extends AppCompatActivity {

    ImageView iv_srchResultImage,
              iv_srchResultRelatedImage1,
              iv_srchResultRelatedImage2,
              iv_srchResultRelatedImage3;

    Button btn_foodLike;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        // 요소를 초기화합니다.
        btn_foodLike = findViewById(R.id.btn_foodLike);

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



    }
}