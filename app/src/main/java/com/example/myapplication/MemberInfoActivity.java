package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MemberInfoActivity extends AppCompatActivity {
    private TextView tv_id, tv_pw, tv_first_name, tv_last_name, tv_age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info);

        tv_id = findViewById(R.id.tv_id);
        tv_pw = findViewById(R.id.tv_pw);
        tv_first_name = findViewById(R.id.tv_fn);
        tv_last_name = findViewById(R.id.tv_ln);
        tv_age = findViewById(R.id.tv_age);

        String id = getIntent().getStringExtra("id");
        String pw = getIntent().getStringExtra("pw");
        String first_name = getIntent().getStringExtra("first_name");
        String last_name = getIntent().getStringExtra("last_name");
        String age = getIntent().getStringExtra("age");


        tv_id.setText(id);
        tv_pw.setText(pw);
        tv_first_name.setText(first_name);
        tv_last_name.setText(last_name);
        tv_age.setText(age);

    }
}