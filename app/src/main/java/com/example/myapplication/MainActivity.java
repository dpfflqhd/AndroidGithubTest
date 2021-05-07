package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navi;
    HomeFragment homeFragment;
    FoodFragment foodFragment;
    CultureFragment cultureFragment;
    ChatFragment chatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navi = findViewById(R.id.bnv);
        homeFragment = new HomeFragment();
        foodFragment = new FoodFragment();
        cultureFragment = new CultureFragment();
        chatFragment = new ChatFragment();

        executeFragment(homeFragment);


        navi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //선택한 메뉴아이템을 클릭시 FrameLayout에 출력
                if(item.getItemId() == R.id.item_home){
                    executeFragment(homeFragment);
                } else if(item.getItemId() == R.id.item_food){
                    executeFragment(foodFragment);
                } else if(item.getItemId() == R.id.item_culture){
                    executeFragment(cultureFragment);
                } else if(item.getItemId() == R.id.item_talk){
                    executeFragment(chatFragment);
                }

                //true:클릭한 메뉴아이템 포커싱 | false : 변화없음
                return true;
            }
        });

    }

    private void executeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
    }

}
