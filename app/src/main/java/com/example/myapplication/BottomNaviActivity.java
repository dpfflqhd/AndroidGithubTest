package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNaviActivity extends AppCompatActivity {

    BottomNavigationView navi;
    FirstFragment frag1;
    SecondFragment frag2;
    public static String email = "";
    public static String email1 = "";
    public static String email2 = "";

    // 이 부분은 재이씨 프래그먼트 전역변수 선언하는 곳입니다.
    ThirdFragment frag3;
    Fragment3Child2 frag5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navi);


        Intent get_intent = getIntent();
        email = get_intent.getStringExtra("id");
        email1 = get_intent.getStringExtra("id");
        email2 = get_intent.getStringExtra("id");
        Log.d("bottom intent id", email);
        Log.d("bottom1 intent id", email1);
        Log.d("bottom2 intent id", email2);

        // 요소 초기화
        navi = findViewById(R.id.b_navi);
        
        // frag1은 마이페이지 프래그먼트입니다.
        frag1 = new FirstFragment();
        Bundle bundle = new Bundle();
        String sendstr = email;
        bundle.putString("send", sendstr );
        frag1.setArguments(bundle);
        
        // frag2는 검색 프래그먼트입니다.
        frag2 = new SecondFragment();
        Bundle bundle1 = new Bundle();
        String sendstr1 = email1;
        Log.d("sendstr1", sendstr1);
        bundle1.putString("send", sendstr1);
        frag2.setArguments(bundle1);

        // 이부분은 재이씨 프래그먼트 초기화하는 곳입니다.
        frag3 = new ThirdFragment();
        frag5 = new Fragment3Child2();
        Bundle bundle2 = new Bundle();
        String sendstr2 = email2;
        bundle2.putString("send", sendstr2);
        frag3.setArguments(bundle2);

        // 첫화면은 검색화면으로 합니다.
        getSupportFragmentManager().beginTransaction().replace(R.id.b_frame, frag2).commit();

        // 바텀 내비게이터 아이템을 클릭했을때의 리스너를 정의하는 곳입니다.
        navi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    // 검색 프래그먼트를 띄워줍니다. exeFrag()의 매개변수에 검색 프래그먼트를 넣어주세요.
                if(item.getItemId() == R.id.item_foodSearch) {
                    exeFrag(frag2);
                
                    // 지도 프래그먼트를 띄워줍니다. exeFrag()의 매개변수에 지도 프래그먼트를 넣어주세요.
                } else if (item.getItemId() == R.id.item_foodMap) {
                    exeFrag(frag3);

                    // 커뮤니티 프래그먼트를 띄워줍니다. exeFrag()의 매개변수에 커뮤니티 프래그먼트를 넣어주세요.
                } else if (item.getItemId() == R.id.item_foodCommunity) {
                    exeFrag(frag5);
                    
                    // 마이페이지 프래그먼트를 띄워줍니다. exeFrag()의 매개변수에 마이페이지 프래그먼트를 넣어주세요.
                } else if (item.getItemId() == R.id.item_foodMypage) {
                    exeFrag(frag1);
                }


                return true;
            }
        });


    }

    // 프래그먼트를 호출하는 명령어 메서드입니다.
    private void exeFrag(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.b_frame, fragment).commit();
    }
}