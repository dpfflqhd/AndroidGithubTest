package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class MapDialog extends Dialog {

    private Context mContext;
    private Button btn_map_search;
    private Button btn_map_cancle;
    private EditText edt_map;
    private TextView map_text, tv_map_rating, tv_map_like, tv_map_addr, tv_map_phone;
    private ImageView iv_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_dialog);

        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btn_map_search = findViewById(R.id.btn_map_search);
        btn_map_cancle = findViewById(R.id.btn_map_cancle);
        map_text = findViewById(R.id.map_text);
        tv_map_rating = findViewById(R.id.tv_map_rating);
        tv_map_like = findViewById(R.id.tv_map_like);
        tv_map_addr = findViewById(R.id.tv_map_addr);
        tv_map_phone = findViewById(R.id.tv_map_phone);
        iv_map = findViewById(R.id.iv_map);

        btn_map_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_map_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });
    }

    public MapDialog(@NonNull Context context) {
        super(context);
        mContext = context;

    }

}
