package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity{

    String dialog_id, dialog_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button btn_start_login = (Button)findViewById(R.id.btn_start_login);
        Button btn_start_join = (Button)findViewById(R.id.btn_start_join);



        btn_start_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다이얼로그 생성하고 보여주기
                LoginDialog loginDialog = new LoginDialog(StartActivity.this);
                loginDialog.show();

                // 다이얼로그에서 로그인버튼 클릭 시 포토액티비티로 이동
                loginDialog.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            Intent photo_intent = new Intent(getApplicationContext(), PhotoActivity.class);
                            startActivity(photo_intent);
                    }
                });
            }
        });

        // 회원가입버튼 클릭 시
        btn_start_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent join_intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(join_intent);
            }
        });
    }

}