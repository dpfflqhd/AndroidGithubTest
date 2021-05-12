package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JoinActivity extends AppCompatActivity {
    EditText edt_age, edt_join_id, edt_join_pw, edt_join_fn, edt_join_ln;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private Button btn_age, btn_join_go;
    String result;
    MemberSQLiteOpenHelper helper;
    static final String DB_NAME = "member3.db";
    static final int DB_VERSION = 1;
    SQLiteDatabase database;

    ArrayList<String> data;
    ArrayAdapter<String> adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        helper = new MemberSQLiteOpenHelper(getApplicationContext(), DB_NAME, null, DB_VERSION);

        database = helper.getWritableDatabase(); //데이터베이스에 데이터를 읽고 쓰는 기능

        initView();

        btn_age = findViewById(R.id.btn_age);

        Date today = new Date();      // birthday 버튼의 초기화를 위해 date 객체와 SimpleDataFormat 사용
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        result = dateFormat.format(today);




        //Date Fragment 관련 메소드 실행
        this.InitializeView();
        this.InitializeListener();


        btn_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerFragment newFragment = new DatePickerFragment();   //DatePickerFragment 객체 생성
                newFragment.show(getSupportFragmentManager(), "datePicker");                //프래그먼트 매니저를 이용하여 프래그먼트 보여주기


            }
        });

        btn_join_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = edt_join_id.getText().toString();
                String pw = edt_join_pw.getText().toString();
                String first_name = edt_join_fn.getText().toString();
                String age = edt_age.getText().toString();
                data.clear();
                insert(id, pw, first_name, age);
//                select();

            }
        });
    }

    private void initView() {
        edt_join_fn = findViewById(R.id.edt_join_fn);
        edt_join_id = findViewById(R.id.edt_join_id);
        edt_join_pw = findViewById(R.id.edt_join_pw);
        btn_join_go = findViewById(R.id.btn_join_go);
        data = new ArrayList<>();
        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1, data);
    }

    public void InitializeView()
    {
        edt_age = findViewById(R.id.edt_age);
    }

    public void InitializeListener()
    {
        callbackMethod = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                edt_age.setText(result);
//                year + "년" + monthOfYear + "월" + dayOfMonth + "일"
            }
        };
    }

    public void OnClickHandler(View view)
    {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, 2019, 5, 24);

        dialog.show();
    } //Age Date설정 끝

    public void delete(String name){

//        database.execSQL("delete from member2 where name = '"+name+"';");
        //delete(테이블명, where조건, 조건에 대입할 값)
        int result = database.delete("member2", "name=?", new String[]{name});


        Log.d("SQLite", "데이터 삭제 완료!");
        Toast.makeText(this, "데이터 삭제 완료!", Toast.LENGTH_SHORT).show();




    }

    public void update(String name, int age, String phone){
        //문자열은 작은따옴표, 숫자는 큰따옴표
//        database.execSQL("update member2 set age="+age+", phone='"+phone+"' where name = '"+name+"';");

        //ContentValues는 insert와 update구문에 대해서만 사용
        ContentValues values = new ContentValues();
        values.put("age", age);
        values.put("phone", phone);


        //new String[]{name} 문자배열 형식으로 보내는 방법
        //new String[]{name, "1"} 2개의 where 조건절에 보내는 방법
        int result = database.update("member2", values, "name=?", new String[]{name});


        Log.d("SQLite", "데이터 수정 완료!");
        Toast.makeText(this, "데이터 수정 완료!", Toast.LENGTH_SHORT).show();


    }

    public void select(){
//        Cursor cursor = database.rawQuery("select * from member2", null);

        //columns : 검색할 컬럼을 문자열 배열로 정의 ex) new String[]{age, phone}
        //selection : where 조건 ex) name=?
        //selectionArgs : where 조건에 들어갈 값을 문자열배열로 정의

        Cursor cursor = database.query("member3", null, null, null, null, null, null);
        adapter.notifyDataSetChanged();
//        lv.clearChoices();

        //rs.next() --> moveToNext()
        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            String pw = cursor.getString(1);
            String first_name = cursor.getString(2);
            String age = cursor.getString(3);

            data.add("아이디:"+ id +" / 비번 : " + pw + "/ 이름:" +first_name + "/ 나이:"+age);
            Log.d("데이터조회 : ", id + "/" + pw + "/" + first_name + "/" + "/" + age);
//
//            Intent login_intent = new Intent(getApplicationContext(), LoginActivity.class);

        }
    }

    public void insert(String id, String pw, String first_name, String age){
//        database.execSQL("insert into member2(name, age, phone) values('"+name+"', "+age+", '"+phone+"');");


        select();

        ContentValues values = new ContentValues();
        //데이터베이스 객체의 insert()에 넘겨줄 객체
        // -key : 테이블의 컬럼명
        values.put("id", id);
        values.put("pw", pw);
        values.put("first_name", first_name);
        values.put("age", age);

        String member_info = id + pw + first_name + age;



        //ContentValues는 long타입으로 반환하기때문에 long타입으로 정의
        long result = database.insert("member3", null, values);
        if(result > 0){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            Toast.makeText(this, "데이터 저장 완료!"+result, Toast.LENGTH_SHORT).show();

            Log.d("SQLite", "데이터 삽입 성공!"+member_info);

        }


    }


}