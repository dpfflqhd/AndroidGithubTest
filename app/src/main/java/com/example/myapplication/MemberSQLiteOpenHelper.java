package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MemberSQLiteOpenHelper extends SQLiteOpenHelper {
    public MemberSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //최초에 한 번 실행되는 메소드 --> 테이블생성
        //정수형 : INTEGER, 문자열 : TEXT, 실수 : REAL,
        db.execSQL("create table member3(id TEXT primary key, pw TEXT, first_name TEXT, age TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //데이터베이스의 버젼이 바뀔 때 실행되는 메소드
        db.execSQL("drop table member3");
        onCreate(db);

    }
}
