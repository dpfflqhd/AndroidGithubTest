package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText edt_login_id, edt_login_pw;
    private Button btn_login;

    MemberSQLiteOpenHelper helper;
    static final String DB_NAME = "member3.db";
    static final int DB_VERSION = 1;
    SQLiteDatabase database;
    ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        helper = new MemberSQLiteOpenHelper(getApplicationContext(), DB_NAME, null, DB_VERSION);

        database = helper.getWritableDatabase(); //데이터베이스에 데이터를 읽고 쓰는 기능

        edt_login_id = findViewById(R.id.edt_login_id);
        edt_login_pw = findViewById(R.id.edt_login_pw);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                select();
            }
        });
    }

    public void select(){
        String login_id = edt_login_id.getText().toString();
        String login_pw = edt_login_pw.getText().toString();

        Cursor cursor = database.rawQuery("select id, pw from member3 where id = '"+login_id+"'", null);

        //rs.next() --> moveToNext()
        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            String pw = cursor.getString(1);
            /*String first_name = cursor.getString(2);
            String last_name = cursor.getString(3);
            String age = cursor.getString(4);*/
            /*data.add("아이디:"+id+" / 비번 : " +pw+ "/ 성 :" +first_name+"/ 이름:"+ last_name + "/ 나이:"+age);
            Log.d("데이터조회 : ", id + "/" + pw + "/" + first_name + "/" + last_name + "/" + age);*/

            Intent main_intent = new Intent(getApplicationContext(), MainSwipeActivity.class);
            main_intent.putExtra("id", login_id);
            Log.d("로그인액티비티, id : ", login_id);

            /*main_intent.putExtra("first_name", first_name);
            main_intent.putExtra("last_name", last_name);*/

            if(id.equals(login_id) && pw.equals(login_pw)){
                startActivity(main_intent);
            } else {
                Toast.makeText(this, "아이디와 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}