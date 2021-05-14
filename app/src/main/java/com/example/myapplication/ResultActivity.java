package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private Button btn_after, btn_before, btn_like, btn_review;
    private TextView tv_count, tv_name, tv_menu, tv_price, tv_tell, tv_addr, tv_time;
    private ImageView img_food, img_reco1, img_reco2, img_reco3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        btn_after = findViewById(R.id.btn_after); btn_before = findViewById(R.id.btn_before);
        btn_like = findViewById(R.id.btn_like);   btn_review = findViewById(R.id.btn_review);

        tv_count = findViewById(R.id.tv_count);   tv_name = findViewById(R.id.tv_name);
        tv_menu = findViewById(R.id.tv_menu);     tv_price = findViewById(R.id.tv_price);
        tv_tell = findViewById(R.id.tv_tell);     tv_addr = findViewById(R.id.tv_addr);
        tv_time = findViewById(R.id.tv_time);

        img_food = findViewById(R.id.img_food);   img_reco1 = findViewById(R.id.img_reco1);
        img_reco2 = findViewById(R.id.img_reco2); img_reco3 = findViewById(R.id.img_reco3);


        // 즐겨찾기 기능
        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_like.setSelected(true);
            }
        });
    }
}