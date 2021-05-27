package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class BottomNaviActivity extends AppCompatActivity {

    BottomNavigationView navi;
    FirstFragment frag1;
    SecondFragment frag2;
    public static String email = "";
    public static String email1 = "";
    public static String email2 = "";
    String name = "";
    String id = "";
    String age = "";
    String real_id = "";
    String real_name = "";

    // 이 부분은 재이씨 프래그먼트 전역변수 선언하는 곳입니다.
    ThirdFragment frag3;
    Fragment3Child2 frag5;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


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



        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                id = document.getString("id");
                                name = document.getString("name");
                                age = document.getString("age");
                                Log.d("bottom get data id", id + name + age);
                                if(email.equals(id)){
                                    real_id = id;
                                    real_name = name;
                                    Log.d("real_id, name", real_id + real_name);
                                }
                            }

                            if(!real_name.equals("")){

                                // 요소 초기화
                                navi = findViewById(R.id.b_navi);

                                // frag1은 마이페이지 프래그먼트입니다.
                                frag1 = new FirstFragment();
                                Bundle bundle = new Bundle();
                                String sendstr = email;
                                bundle.putString("send", sendstr);
                                bundle.putString("name", real_name);
                                Log.d("bottom put intent", real_name);
                                frag1.setArguments(bundle);

                                // frag2는 검색 프래그먼트입니다.
                                // frag2 데이터 전달 추가 -김승환
                                frag2 = new SecondFragment();
                                Bundle bundle1 = new Bundle();
                                String sendstr1 = email1;
                                Log.d("sendstr1", sendstr1);
                                bundle1.putString("send", sendstr1);
                                bundle1.putString("name", real_name);
                                frag2.setArguments(bundle1);

                                // 이부분은 재이씨 프래그먼트 초기화하는 곳입니다.
                                frag3 = new ThirdFragment();
                                Bundle bundle2 = new Bundle();
                                String sendstr2 = email2;
                                bundle2.putString("send", sendstr2);
                                frag3.setArguments(bundle2);

                                frag5 = new Fragment3Child2();

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

                        } else {
                            Log.w("받아오기실패", "Error getting documents.", task.getException());
                        }
                    }
                });





        //        tran.replace(R.id.fragment_test, fragment).commit();





    }

    // 프래그먼트를 호출하는 명령어 메서드입니다.
    private void exeFrag(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.b_frame, fragment).commit();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        getSupportFragmentManager().beginTransaction().replace(R.id.b_frame, frag1).commit();// Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.
    }
}