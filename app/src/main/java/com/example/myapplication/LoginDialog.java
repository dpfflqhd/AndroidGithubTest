package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;


public class LoginDialog extends Dialog implements View.OnClickListener{

    private Context mContext;
    private TextView btn_cancel;
    private TextView btn_ok;
    private EditText edt_loginid, edt_loginpw;
    private String loginid, loginpw;
    private FirebaseAuth mAuth;

    public LoginDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_dialog);

        edt_loginid = findViewById(R.id.edt_loginid);
        edt_loginpw = findViewById(R.id.edt_login_pw);

        btn_cancel = (TextView) findViewById(R.id.btn_cancel);
        btn_ok = (TextView) findViewById(R.id.btn_ok);

        btn_cancel.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            // 취소버튼
            case R.id.btn_cancel:
                dismiss(); // 다이얼로그 닫기
                break;
            // 로그인버튼
            case R.id.btn_ok:
                ((MainActivity) mContext).finish(); //메인 엑티비티 닫기
                dismiss();
                break;

        }
    }

}